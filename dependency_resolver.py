#!/usr/bin/env python3
import os, re, sys, xml.etree.ElementTree as ET
from pathlib import Path
from collections import deque, defaultdict

ROOT = Path(sys.argv[1] if len(sys.argv) >= 2 else ".").resolve()
SEED_BSN = sys.argv[2] if len(sys.argv) >= 3 else "com.avaloq.tools.ddk.xtext.expression.ui"


def read_pom_artifactid(pom_path: Path):
    try:
        ns = {"m": "http://maven.apache.org/POM/4.0.0"}
        tree = ET.parse(pom_path)
        root = tree.getroot()
        aid = root.findtext("m:artifactId", namespaces=ns)
        gid = root.findtext("m:groupId", namespaces=ns) or root.findtext(
            "m:parent/m:groupId", namespaces=ns
        )
        packaging = root.findtext("m:packaging", namespaces=ns) or "jar"
        return gid, aid, packaging
    except Exception:
        return None, None, None


def parse_manifest(manifest_path: Path):
    # Concatenate continuation lines per JAR spec (lines starting with a single space)
    text = manifest_path.read_text(encoding="utf-8", errors="ignore")
    lines = []
    buf = ""
    for raw in text.splitlines():
        if raw.startswith(" "):
            buf += raw[1:]
        else:
            if buf:
                lines.append(buf)
            buf = raw
    if buf:
        lines.append(buf)
    headers = {}
    for line in lines:
        if ":" in line:
            k, v = line.split(":", 1)
            headers[k.strip()] = v.strip()
    return headers


def split_list(header_value):
    # split by commas at top-level (OSGi uses ; for attrs/dirs)
    items = []
    if not header_value:
        return items
    # naive but works for typical manifests
    for part in re.split(r",\s*(?=[^;]+)", header_value):
        part = part.strip()
        if part:
            items.append(part)
    return items


def extract_bsn_token(item):
    # first segment before ';'
    return item.split(";", 1)[0].strip()


def is_optional(item):
    return ";resolution:=optional" in item


# scan repo for bundles (MANIFEST.MF + pom.xml)
bundles = {}
exports_by_pkg = defaultdict(set)

for manifest in ROOT.rglob("META-INF/MANIFEST.MF"):
    headers = parse_manifest(manifest)
    bsn = headers.get("Bundle-SymbolicName", "").split(";")[0].strip()
    if not bsn:
        continue
    pom = manifest.parents[2] / "pom.xml"  # typical layout: module/META-INF/MANIFEST.MF
    if not pom.exists():
        pom = manifest.parents[1] / "pom.xml"
    gid, aid, packaging = read_pom_artifactid(pom)
    if not aid:
        continue
    require = [x for x in split_list(headers.get("Require-Bundle", ""))]
    imports = [x for x in split_list(headers.get("Import-Package", ""))]
    exports = [x for x in split_list(headers.get("Export-Package", ""))]
    export_pkgs = set(extract_bsn_token(x) for x in exports)  # here token == package
    for p in export_pkgs:
        exports_by_pkg[p].add(bsn)
    bundles[bsn] = {
        "pom": pom,
        "groupId": gid,
        "artifactId": aid,
        "packaging": packaging,
        "require": require,
        "imports": imports,
        "exportPkgs": export_pkgs,
    }

if SEED_BSN not in bundles:
    sys.exit(f"Seed bundle not found: {SEED_BSN}")

needed_bsns = set()
queue = deque([SEED_BSN])


def add_require_edges(bsn):
    for item in bundles[bsn]["require"]:
        if is_optional(item):
            continue
        dep = extract_bsn_token(item)
        if dep in bundles and dep not in needed_bsns:
            queue.append(dep)


def add_import_edges(bsn):
    # For each imported package, if a local bundle exports it, include that bundle.
    for item in bundles[bsn]["imports"]:
        if is_optional(item):
            continue
        pkg = extract_bsn_token(item)  # here token == package name
        providers = [prov for prov in exports_by_pkg.get(pkg, []) if prov in bundles]
        for prov in providers:
            if prov not in needed_bsns:
                queue.append(prov)


while queue:
    cur = queue.popleft()
    if cur in needed_bsns:
        continue
    needed_bsns.add(cur)
    add_require_edges(cur)
    add_import_edges(cur)

# Map to Maven modules (artifactIds); keep only eclipse-plugin/feature/rcp/repository types
artifact_ids = []
for bsn in needed_bsns:
    b = bundles[bsn]
    if b["pom"].exists():
        artifact_ids.append(b["artifactId"])

# Deterministic order: sort by name
artifact_ids = sorted(set(artifact_ids))

print("# Modules needed (pass this to mvn -pl):")
modules = ",".join(f":{aid}" for aid in artifact_ids)
print(modules)
print("# Full command")
print(f"mvn compile -f ddk-parent/pom.xml -T4C -pl {modules}")
