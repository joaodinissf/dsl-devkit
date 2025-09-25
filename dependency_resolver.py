#!/usr/bin/env python3
"""Resolve local Eclipse bundles needed to build a given bundle."""

from __future__ import annotations

import argparse
import re
import sys
import xml.etree.ElementTree as ET
from collections import defaultdict, deque
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable, Iterator


@dataclass(frozen=True)
class Bundle:
    bsn: str
    pom: Path
    group_id: str | None
    artifact_id: str | None
    packaging: str | None
    require: tuple[str, ...]
    imports: tuple[str, ...]
    export_packages: frozenset[str]


def parse_args(argv: Iterable[str] | None = None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Collect required Eclipse bundles for a given seed bundle.",
    )
    parser.add_argument("root", nargs="?", default=".", help="Repository root to scan")
    parser.add_argument(
        "seed",
        nargs="?",
        default="com.avaloq.tools.ddk.xtext.expression.ui",
        help="Bundle-SymbolicName to start dependency collection from",
    )
    parser.add_argument(
        "--include-optional",
        dest="include_optional",
        action=argparse.BooleanOptionalAction,
        default=True,
        help="Include dependencies marked resolution:=optional (default: include)",
    )
    return parser.parse_args(argv)


def read_pom_metadata(pom_path: Path) -> tuple[str | None, str | None, str | None]:
    try:
        ns = {"m": "http://maven.apache.org/POM/4.0.0"}
        tree = ET.parse(pom_path)
        root = tree.getroot()
        artifact_id = root.findtext("m:artifactId", namespaces=ns)
        group_id = root.findtext("m:groupId", namespaces=ns) or root.findtext(
            "m:parent/m:groupId", namespaces=ns
        )
        packaging = root.findtext("m:packaging", namespaces=ns) or "jar"
        return group_id, artifact_id, packaging
    except Exception:
        return None, None, None


def parse_manifest(manifest_path: Path) -> dict[str, str]:
    # Concatenate continuation lines per JAR spec (lines starting with a single space)
    text = manifest_path.read_text(encoding="utf-8", errors="ignore")
    lines: list[str] = []
    buffer = ""
    for raw in text.splitlines():
        if raw.startswith(" "):
            buffer += raw[1:]
        else:
            if buffer:
                lines.append(buffer)
            buffer = raw
    if buffer:
        lines.append(buffer)
    headers: dict[str, str] = {}
    for line in lines:
        if ":" in line:
            key, value = line.split(":", 1)
            headers[key.strip()] = value.strip()
    return headers


def split_header_list(value: str | None) -> tuple[str, ...]:
    if not value:
        return tuple()
    items = []
    for part in re.split(r",\s*(?=[^;]+)", value):
        part = part.strip()
        if part:
            items.append(part)
    return tuple(items)


def extract_token(item: str) -> str:
    return item.split(";", 1)[0].strip()


def is_optional(item: str) -> bool:
    return ";resolution:=optional" in item


def discover_bundles(root: Path) -> tuple[dict[str, Bundle], dict[str, set[str]]]:
    bundles: dict[str, Bundle] = {}
    exports_by_pkg: dict[str, set[str]] = defaultdict(set)
    fragment_hosts: dict[str, str] = {}

    for manifest in root.rglob("META-INF/MANIFEST.MF"):
        headers = parse_manifest(manifest)
        bsn = headers.get("Bundle-SymbolicName", "").split(";")[0].strip()
        if not bsn:
            continue

        pom = manifest.parents[2] / "pom.xml"
        if not pom.exists():
            pom = manifest.parents[1] / "pom.xml"

        group_id, artifact_id, packaging = read_pom_metadata(pom)
        if not artifact_id:
            continue

        require = split_header_list(headers.get("Require-Bundle"))
        imports = split_header_list(headers.get("Import-Package"))
        exports = split_header_list(headers.get("Export-Package"))
        export_packages = frozenset(extract_token(pkg) for pkg in exports)
        fragment_host = headers.get("Fragment-Host")
        if fragment_host:
            fragment_hosts[bsn] = extract_token(fragment_host)

        for pkg in export_packages:
            exports_by_pkg[pkg].add(bsn)

        bundles[bsn] = Bundle(
            bsn=bsn,
            pom=pom,
            group_id=group_id,
            artifact_id=artifact_id,
            packaging=packaging,
            require=require,
            imports=imports,
            export_packages=export_packages,
        )

    return bundles, exports_by_pkg, fragment_hosts


def collect_required_bsns(
    bundles: dict[str, Bundle],
    exports_by_pkg: dict[str, set[str]],
    fragment_hosts: dict[str, str],
    seed_bsn: str,
    include_optional: bool,
) -> set[str]:
    if seed_bsn not in bundles:
        raise SystemExit(f"Seed bundle not found: {seed_bsn}")

    needed: set[str] = set()
    queue: deque[str] = deque([seed_bsn])

    def maybe_enqueue(bsn: str) -> None:
        if bsn in bundles and bsn not in needed:
            queue.append(bsn)

    while queue:
        current = queue.popleft()
        if current in needed:
            continue
        needed.add(current)

        host_bsn = fragment_hosts.get(current)
        if host_bsn:
            maybe_enqueue(host_bsn)

        for item in bundles[current].require:
            if not include_optional and is_optional(item):
                continue
            maybe_enqueue(extract_token(item))

        for item in bundles[current].imports:
            if not include_optional and is_optional(item):
                continue
            providers = exports_by_pkg.get(extract_token(item), set())
            for provider in providers:
                maybe_enqueue(provider)

    return needed


def bundles_to_modules(bundles: dict[str, Bundle], bsns: Iterable[str]) -> list[str]:
    artifact_ids = {
        bundles[bsn].artifact_id
        for bsn in bsns
        if bsn in bundles and bundles[bsn].artifact_id
    }
    return sorted(artifact_ids)


def format_modules_for_output(modules: Iterable[str]) -> str:
    return ",".join(f":{module}" for module in modules)


def main(argv: Iterable[str] | None = None) -> int:
    args = parse_args(argv)
    root = Path(args.root).resolve()

    bundles, exports_by_pkg, fragment_hosts = discover_bundles(root)
    needed_bsns = collect_required_bsns(
        bundles,
        exports_by_pkg,
        fragment_hosts,
        args.seed,
        args.include_optional,
    )
    modules = bundles_to_modules(bundles, needed_bsns)

    print("# Modules needed (pass this to mvn -pl):")
    module_list = format_modules_for_output(modules)
    print(module_list)
    print("# Full command")
    print(f"mvn compile -f ddk-parent/pom.xml -T4C -pl {module_list}")

    return 0


if __name__ == "__main__":
    sys.exit(main())
