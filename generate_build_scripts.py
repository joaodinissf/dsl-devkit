#!/usr/bin/env python3
"""Generate build shell scripts for every local com.avaloq.* bundle."""

from __future__ import annotations

import argparse
import subprocess
import sys
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
from typing import Iterable

try:
    from tqdm import tqdm
except ImportError:  # pragma: no cover - optional dependency
    tqdm = None


def parse_args(argv: Iterable[str] | None = None) -> argparse.Namespace:
    repo_root = Path(__file__).resolve().parent
    parser = argparse.ArgumentParser(
        description="Generate mvn compile shell scripts for local Avaloq bundles.",
    )
    parser.add_argument(
        "root",
        nargs="?",
        default=repo_root,
        type=Path,
        help="Repository root to scan (default: script directory)",
    )
    parser.add_argument(
        "--output-dir",
        default=Path("build-scripts"),
        type=Path,
        help="Directory to store generated shell scripts (default: build-scripts relative to ROOT)",
    )
    parser.add_argument(
        "--include-optional",
        dest="include_optional",
        action=argparse.BooleanOptionalAction,
        default=True,
        help="Include optional dependencies when resolving bundles (default: include)",
    )
    parser.add_argument(
        "--resolver",
        default=repo_root / "dependency_resolver.py",
        type=Path,
        help="Path to dependency_resolver.py (default: alongside this script)",
    )
    parser.add_argument(
        "--jobs",
        "-j",
        default=4,
        type=int,
        help="Number of bundles to resolve in parallel (default: 4)",
    )
    return parser.parse_args(list(argv) if argv is not None else None)


def find_bundle_dirs(root: Path) -> list[Path]:
    """Return repository directories that look like Eclipse bundles."""
    return sorted(
        path
        for path in root.iterdir()
        if path.is_dir() and path.name.startswith("com.avaloq.")
    )


def read_bundle_symbolic_name(project_dir: Path) -> str | None:
    manifest = project_dir / "META-INF" / "MANIFEST.MF"
    if not manifest.exists():
        return None
    for line in manifest.read_text(encoding="utf-8", errors="ignore").splitlines():
        if line.lower().startswith("bundle-symbolicname:"):
            value = line.split(":", 1)[1].strip()
            return value.split(";", 1)[0].strip()
    return None


def resolve_build_command(
    resolver: Path,
    root: Path,
    bundle_symbolic_name: str,
    include_optional: bool,
) -> str:
    """Run dependency_resolver and return the mvn command line."""
    cmd = [
        sys.executable,
        str(resolver),
        str(root),
        bundle_symbolic_name,
    ]
    if not include_optional:
        cmd.append("--no-include-optional")
    result = subprocess.run(
        cmd,
        cwd=root,
        check=True,
        capture_output=True,
        text=True,
    )
    for line in reversed(result.stdout.strip().splitlines()):
        line = line.strip()
        if line.startswith("mvn "):
            return line
    raise RuntimeError(
        f"dependency_resolver did not return a Maven command for {bundle_symbolic_name}"
    )


def write_shell_script(output_dir: Path, project_dir: Path, command: str) -> None:
    output_dir.mkdir(parents=True, exist_ok=True)
    target = output_dir / f"{project_dir.name}.sh"
    target.write_text(f"{command}\n", encoding="utf-8")


def process_bundle(
    project_dir: Path,
    bsn: str,
    resolver: Path,
    root: Path,
    include_optional: bool,
    output_dir: Path,
) -> tuple[str, str, str | None, int]:
    try:
        command = resolve_build_command(
            resolver,
            root,
            bsn,
            include_optional,
        )
        write_shell_script(output_dir, project_dir, command)
        return ("ok", project_dir.name, None, 0)
    except subprocess.CalledProcessError as exc:
        stdout = (exc.stdout or "").strip()
        stderr = (exc.stderr or "").strip()
        error_text = stderr or stdout or str(exc)
        if "Seed bundle not found" in error_text:
            return ("skip", project_dir.name, error_text, 0)
        return ("error", project_dir.name, error_text, exc.returncode or 1)
    except Exception as exc:  # pragma: no cover - defensive
        return ("error", project_dir.name, str(exc), 1)


def main(argv: Iterable[str] | None = None) -> int:
    args = parse_args(argv)
    root = args.root.resolve()
    output_dir = args.output_dir if args.output_dir.is_absolute() else (root / args.output_dir)
    resolver = args.resolver if args.resolver.is_absolute() else (root / args.resolver)

    bundles = find_bundle_dirs(root)
    if not bundles:
        print("No com.avaloq.* directories found", file=sys.stderr)
        return 1

    bundle_entries = []
    skipped_missing_manifest = []
    for project_dir in bundles:
        bsn = read_bundle_symbolic_name(project_dir)
        if not bsn:
            if (project_dir / "feature.xml").exists():
                print(f"[INFO] Skipping feature project {project_dir.name}")
            else:
                skipped_missing_manifest.append(project_dir.name)
            continue
        bundle_entries.append((project_dir, bsn))

    if not bundle_entries:
        print("No bundles with MANIFEST.MF found to process", file=sys.stderr)
        return 1

    skipped_resolver = []
    fatal_error: tuple[str, str, int] | None = None

    progress = tqdm(total=len(bundle_entries), desc="Generating", unit="bundle") if tqdm else None

    with ThreadPoolExecutor(max_workers=max(1, args.jobs)) as executor:
        futures = [
            executor.submit(
                process_bundle,
                project_dir,
                bsn,
                resolver,
                root,
                args.include_optional,
                output_dir,
            )
            for project_dir, bsn in bundle_entries
        ]

        for future in as_completed(futures):
            status, name, message, code = future.result()
            if progress:
                progress.update(1)
            if status == "skip":
                skipped_resolver.append(name)
                print(f"Skipping {name}: {message}", file=sys.stderr)
            elif status == "error":
                print(
                    f"dependency_resolver failed for {name}: {message}",
                    file=sys.stderr,
                )
                if not fatal_error:
                    fatal_error = (name, message or "Unknown error", code or 1)

    if progress:
        progress.close()

    if skipped_missing_manifest:
        summary = ", ".join(skipped_missing_manifest)
        print(f"Skipped directories without Bundle-SymbolicName: {summary}", file=sys.stderr)

    if skipped_resolver:
        summary = ", ".join(skipped_resolver)
        print(
            f"Skipped bundles unresolved by dependency_resolver: {summary}",
            file=sys.stderr,
        )

    if fatal_error:
        _, _, code = fatal_error
        return code or 1

    return 0


if __name__ == "__main__":
    sys.exit(main())
