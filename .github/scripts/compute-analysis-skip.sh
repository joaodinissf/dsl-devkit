#!/usr/bin/env bash
#
# Scope static analysis (SpotBugs, or PMD/CPD/Checkstyle) to a pull request's
# changed modules.
#
# Default is RUN (analyze). On a PR this injects <TOOL.skip>true</TOOL.skip>
# properties into every UNCHANGED reactor module's pom, so the analysis mojos
# skip those modules — for SpotBugs that also skips the per-module JVM fork
# (SpotBugsMojo gates on `skip` before forking). A changed module is still
# analysed with its complete aux-classpath: the -am-pulled unchanged
# dependencies compile but are not analysed. Master/snapshot builds run a full
# scan; this script is invoked on pull_request only.
#
# Why this and not -Dspotbugs.onlyAnalyze: onlyAnalyze is one clean flag, but SpotBugs
# applies its class screener too late (after the per-module fork + class scan), so it
# only trimmed ~17% of the goal vs ~88% for this per-module skip (measured on this
# reactor). A small upstream SpotBugs early-exit (skip the run when no application class
# matches the screener) would make onlyAnalyze competitive; if that ever lands, switch
# to onlyAnalyze and delete the spotbugs mode here (tracked in #1455 /
# spotbugs/spotbugs#3796).
#
# On top of the skips, the changed reactor modules are exported as
# SPOTBUGS_SCOPE_ARGS / LINT_SCOPE_ARGS ("-pl <changed> -am") so the lane builds
# only those modules plus their upstream dependencies instead of the full reactor.
#
# Run from the repository root.  Usage: compute-analysis-skip.sh <base-sha> <spotbugs|lint>
set -euo pipefail
base="${1:?base sha required}"
mode="${2:?mode required: spotbugs|lint}"

case "$mode" in
  spotbugs) props="spotbugs.skip"; scope_var="SPOTBUGS_SCOPE_ARGS" ;;
  lint)     props="pmd.skip cpd.skip checkstyle.skip"; scope_var="LINT_SCOPE_ARGS" ;;
  *) echo "unknown mode: $mode" >&2; exit 2 ;;
esac

changed=$(git diff --name-only --diff-filter=ACMR "${base}...HEAD")

# 1) A change to shared build/config can affect any module -> full scan (skip nothing).
#    ddk-configuration holds the analyzers' rulesets and filters, so it counts too.
#    Fail safe: the worst case here is "analyse everything", never "analyse nothing".
while IFS= read -r f; do
  [ -n "$f" ] || continue
  case "$f" in
    pom.xml | ddk-parent/* | .mvn/* | *.target | .github/* | ddk-configuration/* | *[Ss]pot[Bb]ugs*[Ee]xclude*)
      echo "Build/config change ($f) -> full ${mode} scan (no skips)."
      exit 0
      ;;
  esac
done <<EOF
${changed}
EOF

# 2) Changed top-level module directories (the reactor module == top-level dir here).
changed_mods=$(printf '%s\n' "${changed}" | grep '/' | cut -d/ -f1 | sort -u)

# 3) Reactor module dirs from ddk-parent's <modules> (strip the leading ../).
#    ddk-parent is NOT in its own <modules>, so it can never be skip-injected — which
#    is what prevents an accidental inherited (global) skip.
module_dirs=$(grep -oE '<module>\.\./[^<]+</module>' ddk-parent/pom.xml \
  | sed -E 's#.*\.\./([^<]+)</module>#\1#')

# 4) Idempotently inject the skip properties; handle poms with and without <properties>.
#    sed -i.bak + rm is portable across GNU (CI) and BSD (local) sed.
inject_skip() {
  local pom="$1/pom.xml" prop
  [ -f "$pom" ] || return 0
  for prop in $props; do
    if grep -q "<${prop//./\\.}>" "$pom"; then continue; fi
    if grep -q '<properties>' "$pom"; then
      sed -i.bak "s#<properties>#<properties>\n    <${prop}>true</${prop}>#" "$pom"
    else
      sed -i.bak "s#</project>#  <properties>\n    <${prop}>true</${prop}>\n  </properties>\n</project>#" "$pom"
    fi
    rm -f "$pom.bak"
  done
}

# 5) Skip every reactor module that was not touched by this PR.
kept=0
skipped=0
kept_pl=""
while IFS= read -r mod; do
  [ -n "$mod" ] || continue
  if printf '%s\n' "${changed_mods}" | grep -qx "$mod"; then
    kept=$((kept + 1))
    kept_pl="${kept_pl:+${kept_pl},}../${mod}"
  else
    inject_skip "$mod"
    skipped=$((skipped + 1))
  fi
done <<EOF
${module_dirs}
EOF

# 6) Scope the reactor to the changed modules + their upstream dependencies. With no
#    changed reactor module (e.g. a docs-only PR) the full reactor builds with every
#    analysis skipped — same result, no flags needed.
if [ "$kept" -gt 0 ] && [ -n "${GITHUB_ENV:-}" ]; then
  echo "${scope_var}=-pl ${kept_pl} -am" >> "$GITHUB_ENV"
fi

echo "${mode} scope: scanning ${kept} changed module(s), skipping ${skipped} unchanged."
echo "Changed modules: ${changed_mods:-<none>}"
if [ -n "${kept_pl}" ]; then
  echo "Reactor scope args: -pl ${kept_pl} -am"
else
  echo "Reactor scope args: <full reactor>"
fi
