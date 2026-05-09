#!/usr/bin/env python3
"""Annotate PMD/CPD/Checkstyle/SpotBugs violations as GitHub workflow commands.

Walks `target/` reports under ``GITHUB_WORKSPACE``, emits one ``::warning`` or
``::error`` per violation (rendered inline on the PR's Files-changed view), and
exits 1 if any violation was found so the workflow step fails fast.

Also prints a coverage summary (modules-analyzed vs reactor-with-src) so
silently-skipped modules are visible.
"""
import argparse
import os
import sys
from pathlib import Path
from xml.etree import ElementTree as ET

# Both PMD and CPD reports use a default XML namespace, so plain findall('file')
# silently returns nothing. Use {*} wildcard so the parser works regardless of
# whether a future plugin version drops or changes the namespace URI.
ANY_NS = '{*}'


def make_emit(root: Path):
    count = 0

    def emit(level: str, file, line, title: str, msg: str | None) -> None:
        nonlocal count
        count += 1
        text = (msg or '').strip().replace('\n', ' ')[:1000]
        try:
            rel = Path(file).relative_to(root)
        except ValueError:
            rel = file
        print(f"::{level} file={rel},line={line},title={title}::{text}")

    return emit, lambda: count


def parse_pmd(root: Path, emit) -> int:
    """Parse target/pmd.xml. Note PMD 7+ uses a default namespace
    (xmlns="http://pmd.sourceforge.net/report/2.0.0"); use {*} wildcards.
    """
    found = 0
    for xml in root.rglob('target/pmd.xml'):
        found += 1
        for f in ET.parse(xml).getroot().findall(f'{ANY_NS}file'):
            for v in f.findall(f'{ANY_NS}violation'):
                level = 'error' if v.attrib.get('priority') == '1' else 'warning'
                emit(level, f.attrib['name'], v.attrib.get('beginline', '1'),
                     f"PMD/{v.attrib.get('rule', '')}", v.text)
    return found


def parse_cpd(root: Path, emit) -> int:
    """Parse target/cpd.xml. Schema is namespaced (xmlns="https://pmd-code.org/schema/cpd-report").

    Each <duplication> has multiple <file> children pointing to the duplicated locations.
    Emit one warning per duplicated location with line info.
    """
    found = 0
    for xml in root.rglob('target/cpd.xml'):
        found += 1
        tree_root = ET.parse(xml).getroot()
        for dup in tree_root.findall(f'{ANY_NS}duplication'):
            lines = dup.attrib.get('lines', '?')
            tokens = dup.attrib.get('tokens', '?')
            msg = f"Duplicated block of {lines} lines / {tokens} tokens"
            for f in dup.findall(f'{ANY_NS}file'):
                emit('warning', f.attrib.get('path', '?'),
                     f.attrib.get('line', '1'),
                     'CPD/Duplication', msg)
    return found


def parse_checkstyle(root: Path, emit) -> int:
    """Parse target/checkstyle-result.xml. Schema is unnamespaced as of 13.x,
    but use {*} wildcards for forward-compatibility.
    """
    found = 0
    for xml in root.rglob('target/checkstyle-result.xml'):
        found += 1
        for f in ET.parse(xml).getroot().findall(f'{ANY_NS}file'):
            for e in f.findall(f'{ANY_NS}error'):
                level = 'error' if e.attrib.get('severity') == 'error' else 'warning'
                emit(level, f.attrib['name'], e.attrib.get('line', '1'),
                     f"Checkstyle/{e.attrib.get('source', '').split('.')[-1]}",
                     e.attrib.get('message', ''))
    return found


def parse_spotbugs(root: Path, emit) -> int:
    """Parse target/spotbugsXml.xml.

    SpotBugs sourcepath is package-relative (e.g. ``com/avaloq/tools/ddk/Foo.java``),
    not repo-relative — combine with the module's source root so GitHub renders the
    annotation inline on the file in the PR's Files-changed view.

    Tolerates missing SourceLine (line=1, file unknown) and missing LongMessage
    (falls back to ShortMessage, then to the bug type).
    """
    found = 0
    for xml in root.rglob('target/spotbugsXml.xml'):
        found += 1
        module_dir = xml.parent.parent
        for b in ET.parse(xml).getroot().findall(f'{ANY_NS}BugInstance'):
            bug_type = b.attrib.get('type', 'Unknown')
            sl = b.find(f'{ANY_NS}SourceLine')
            lm = b.find(f'{ANY_NS}LongMessage')
            sm = b.find(f'{ANY_NS}ShortMessage')
            text = None
            if lm is not None and lm.text:
                text = lm.text
            elif sm is not None and sm.text:
                text = sm.text
            else:
                text = bug_type

            if sl is not None:
                sourcepath = sl.attrib.get('sourcepath', '?')
                line = sl.attrib.get('start', '1')
                file_path = None
                for src_root in ('src', 'src/main/java', 'src-gen', 'src/main/xtend-gen'):
                    candidate = module_dir / src_root / sourcepath
                    if candidate.exists():
                        file_path = candidate
                        break
                if file_path is None:
                    file_path = module_dir / sourcepath
            else:
                # No SourceLine — surface anyway so the bug is visible
                file_path = '?'
                line = '1'

            emit('warning', file_path, line, f"SpotBugs/{bug_type}", text)
    return found


def coverage_summary(root: Path, kind: str, found: int) -> None:
    """Print a one-line coverage summary so silently-skipped modules are visible.

    Heuristic: count reactor modules that have a ``src/`` directory.
    A normal Tycho reactor has a few module-dirs with no source (parent, target,
    features, repositories) — those are not expected to have reports.
    """
    expected = 0
    for pom in root.rglob('pom.xml'):
        if 'target' in pom.parts:
            continue
        if (pom.parent / 'src').is_dir():
            expected += 1
    if expected == 0:
        return
    pct = (found / expected) * 100 if expected else 0
    print(f"{kind} coverage: {found} reports / {expected} expected modules with src/ ({pct:.0f}%)")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--pmd', action='store_true', help='annotate PMD violations')
    parser.add_argument('--cpd', action='store_true', help='annotate CPD violations')
    parser.add_argument('--checkstyle', action='store_true', help='annotate Checkstyle violations')
    parser.add_argument('--spotbugs', action='store_true', help='annotate SpotBugs violations')
    args = parser.parse_args()

    if not (args.pmd or args.cpd or args.checkstyle or args.spotbugs):
        parser.error('pick at least one of --pmd, --cpd, --checkstyle, --spotbugs')

    root = Path(os.environ.get('GITHUB_WORKSPACE', '.'))
    emit, total = make_emit(root)

    if args.pmd:
        coverage_summary(root, 'PMD', parse_pmd(root, emit))
    if args.cpd:
        coverage_summary(root, 'CPD', parse_cpd(root, emit))
    if args.checkstyle:
        coverage_summary(root, 'Checkstyle', parse_checkstyle(root, emit))
    if args.spotbugs:
        coverage_summary(root, 'SpotBugs', parse_spotbugs(root, emit))

    kinds = ' + '.join(
        k for k, on in (
            ('PMD', args.pmd),
            ('CPD', args.cpd),
            ('Checkstyle', args.checkstyle),
            ('SpotBugs', args.spotbugs),
        ) if on
    )
    print(f"{kinds} violations: {total()}")
    return 1 if total() > 0 else 0


if __name__ == '__main__':
    sys.exit(main())
