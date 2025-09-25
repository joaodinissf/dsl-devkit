#!/usr/bin/env python3
"""Execute generated build scripts with GNU parallel."""

from __future__ import annotations

import argparse
import subprocess
import sys
from pathlib import Path
from typing import Iterable


def parse_args(argv: Iterable[str] | None = None) -> argparse.Namespace:
    repo_root = Path(__file__).resolve().parent
    parser = argparse.ArgumentParser(
        description="Validate generated build scripts by running them with GNU parallel.",
    )
    parser.add_argument(
        "root",
        nargs="?",
        default=repo_root,
        type=Path,
        help="Repository root containing the scripts (default: script directory)",
    )
    parser.add_argument(
        "--scripts-dir",
        default=Path("build-scripts"),
        type=Path,
        help="Directory containing generated .sh scripts (default: build-scripts relative to ROOT)",
    )
    parser.add_argument(
        "--jobs",
        "-j",
        default=2,
        type=int,
        help="Maximum number of parallel jobs (default: 2)",
    )
    parser.add_argument(
        "--parallel",
        default="parallel",
        help="GNU parallel executable to invoke (default: parallel)",
    )
    return parser.parse_args(list(argv) if argv is not None else None)


def gather_scripts(scripts_dir: Path) -> list[Path]:
    return sorted(scripts_dir.glob("*.sh"))


def main(argv: Iterable[str] | None = None) -> int:
    args = parse_args(argv)
    root = args.root.resolve()
    scripts_dir = args.scripts_dir if args.scripts_dir.is_absolute() else (root / args.scripts_dir)
    scripts = gather_scripts(scripts_dir)

    if not scripts:
        print(f"No build scripts found in {scripts_dir}", file=sys.stderr)
        return 1

    cmd = [
        args.parallel,
        f"-j{args.jobs}",
        "--eta",
        "--halt",
        "now,fail=1",
        "bash {}",
        ":::",
        *map(str, scripts),
    ]

    try:
        process = subprocess.run(cmd, cwd=root)
    except FileNotFoundError as exc:
        print(f"Failed to run {args.parallel}: {exc}", file=sys.stderr)
        return 1

    if process.returncode == 0:
        print(f"All {len(scripts)} build scripts completed successfully.")
        return 0

    print("At least one build script failed.", file=sys.stderr)
    return 1


if __name__ == "__main__":
    sys.exit(main())
