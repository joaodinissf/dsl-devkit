#!/usr/bin/env bash
#
# Compute the SpotBugs analysis scope for a pull request.
#
# SpotBugs' bytecode analysis is the long pole of CI, but a PR only needs the
# classes it touched checked. This maps the PR's changed Java sources to their
# packages and emits a `spotbugs.onlyAnalyze` value (package wildcards), so the
# spotbugs job compiles the full reactor (for a correct aux-classpath) but only
# *analyzes* the changed packages.
#
# Package wildcards (`com.foo.*`) are used rather than bare class names because
# naming `com.foo.Bar` would miss its inner/anonymous classes (`Bar$1.class`);
# the package form covers them. Master pushes run a full scan (snapshot.yml), so
# findings in unchanged code are not lost.
#
# Outputs (to $GITHUB_OUTPUT):
#   scope=full            run SpotBugs on everything (build/config changed, or an
#                         unmappable source layout was seen -> stay safe)
#   scope=skip            no analyzable Java changed -> skip SpotBugs
#   scope=only            analyze only changed packages
#   onlyAnalyze=<list>    comma-separated package wildcards (when scope=only)
#
# Usage: compute-spotbugs-scope.sh <base-sha>
set -euo pipefail

base="${1:?base sha required}"

emit() { echo "$1" >>"${GITHUB_OUTPUT:-/dev/stdout}"; }

# Added/Copied/Modified/Renamed only: a deleted class has no bytecode to analyze.
changed=$(git diff --name-only --diff-filter=ACMR "${base}...HEAD")

# Anything that can shift analysis globally -> full scan.
while IFS= read -r f; do
  [ -n "$f" ] || continue
  case "$f" in
    *pom.xml | .mvn/* | *.target | .github/workflows/* | *[Ss]potbugs*[Ee]xclude*)
      echo "Global build/config change ($f) -> full SpotBugs scan"
      emit "scope=full"
      exit 0
      ;;
  esac
done <<EOF
${changed}
EOF

# Map each changed *.java under a recognized source root to its package.
tmp=$(mktemp)
trap 'rm -f "${tmp}"' EXIT
while IFS= read -r f; do
  [ -n "$f" ] || continue
  case "$f" in *.java) ;; *) continue ;; esac
  # Strip '<module>/<source-root>/' so the remainder is the package path.
  rel=$(printf '%s\n' "$f" | sed -E 's#^[^/]+/(src|src-gen|emf-gen|xtend-gen|src-test)/##')
  if [ "$rel" = "$f" ]; then
    # Java file outside a known source root: don't risk a partial scan.
    echo "Unrecognized source layout for $f -> full SpotBugs scan"
    emit "scope=full"
    exit 0
  fi
  pkg=$(dirname "$rel" | tr '/' '.')
  if [ -n "$pkg" ] && [ "$pkg" != "." ]; then
    echo "${pkg}.*" >>"${tmp}"
  fi
done <<EOF
${changed}
EOF

if [ ! -s "${tmp}" ]; then
  echo "No analyzable Java changes -> skip SpotBugs"
  emit "scope=skip"
  exit 0
fi

only=$(sort -u "${tmp}" | paste -sd, -)
echo "SpotBugs scoped to changed packages: ${only}"
emit "scope=only"
emit "onlyAnalyze=${only}"
