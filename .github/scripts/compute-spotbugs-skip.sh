#!/usr/bin/env bash
#
# Scope SpotBugs to a pull request's changed modules.
#
# Default is RUN (analyze). On a PR this injects <spotbugs.skip>true</spotbugs.skip>
# into every UNCHANGED reactor module's pom, so spotbugs-maven-plugin skips the goal —
# and therefore the per-module JVM fork (SpotBugsMojo gates on `skip` before forking) —
# for those modules. The full-reactor compile is left intact (a changed module is still
# analysed with its complete aux-classpath). Master/snapshot builds run a full scan;
# this script is invoked on pull_request only.
#
# Why this and not -Dspotbugs.onlyAnalyze: onlyAnalyze is one clean flag, but SpotBugs
# applies its class screener too late (after the per-module fork + class scan), so it
# only trimmed ~17% of the goal vs ~88% for this per-module skip (measured on this
# reactor). A small upstream SpotBugs early-exit (skip the run when no application class
# matches the screener) would make onlyAnalyze competitive; if that ever lands, switch
# to onlyAnalyze and delete this script (tracked in #1455 / spotbugs/spotbugs#3796).
#
# On top of the skips, the changed reactor modules are exported as SPOTBUGS_SCOPE_ARGS
# ("-pl <changed> -am") so the lane builds only those modules plus their upstream
# dependencies instead of the full reactor. The -am-pulled unchanged dependencies still
# carry the injected skip: they compile (complete aux-classpath) but are not analysed.
#
# Run from the repository root.  Usage: compute-spotbugs-skip.sh <base-sha>
set -euo pipefail
base="${1:?base sha required}"

changed=$(git diff --name-only --diff-filter=ACMR "${base}...HEAD")

# 1) A change to shared build/config can affect any module -> full scan (skip nothing).
#    Fail safe: the worst case here is "analyse everything", never "analyse nothing".
while IFS= read -r f; do
  [ -n "$f" ] || continue
  case "$f" in
    pom.xml | ddk-parent/* | .mvn/* | *.target | .github/* | *[Ss]pot[Bb]ugs*[Ee]xclude*)
      echo "Build/config change ($f) -> full SpotBugs scan (no skips)."
      if [ -n "${GITHUB_ENV:-}" ]; then
        echo "SPOTBUGS_FULL_SCAN=true" >> "$GITHUB_ENV"
      fi
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

# 4) Idempotently inject the skip property; handle poms with and without <properties>.
#    sed -i.bak + rm is portable across GNU (CI) and BSD (local) sed.
inject_skip() {
  local pom="$1/pom.xml"
  [ -f "$pom" ] || return 0
  if grep -q '<spotbugs\.skip>' "$pom"; then return 0; fi
  if grep -q '<properties>' "$pom"; then
    sed -i.bak 's#<properties>#<properties>\n    <spotbugs.skip>true</spotbugs.skip>#' "$pom"
  else
    sed -i.bak 's#</project>#  <properties>\n    <spotbugs.skip>true</spotbugs.skip>\n  </properties>\n</project>#' "$pom"
  fi
  rm -f "$pom.bak"
}

# 5) Skip every reactor module that was not touched by this PR. Kept modules with a
#    bundle MANIFEST are expected to produce an analysis report — the gate checks this
#    so a swallowed resolution/compile failure can never pass as "nothing to scan".
kept=0
skipped=0
kept_pl=""
expect_reports=""
while IFS= read -r mod; do
  [ -n "$mod" ] || continue
  if printf '%s\n' "${changed_mods}" | grep -qx "$mod"; then
    kept=$((kept + 1))
    kept_pl="${kept_pl:+${kept_pl},}../${mod}"
    # Only bundles with sources reliably emit a report (a source-less bundle,
    # e.g. pure branding, has nothing for the analyzer to write a SARIF about).
    if [ -f "${mod}/META-INF/MANIFEST.MF" ] && [ -d "${mod}/src" ]; then
      expect_reports="${expect_reports:+${expect_reports} }${mod}"
    fi
  else
    inject_skip "$mod"
    skipped=$((skipped + 1))
  fi
done <<EOF
${module_dirs}
EOF

# 6) Scope the reactor to the changed modules + their upstream dependencies. ddk-target
#    is always kept in the -pl list: the target-definition artifact is referenced by
#    target-platform-configuration, not by any MANIFEST, so -am never pulls it — without
#    it in the reactor Tycho falls back to a local-repository copy, which fails on a
#    cold cache and can silently resolve a stale target definition on a warm one.
#    With no changed reactor module (e.g. a docs-only PR) the full reactor builds with
#    every analysis skipped — same result, no flags needed.
if [ "$kept" -gt 0 ] && [ -n "${GITHUB_ENV:-}" ]; then
  echo "SPOTBUGS_SCOPE_ARGS=-pl ../ddk-target,${kept_pl} -am" >> "$GITHUB_ENV"
  echo "SPOTBUGS_EXPECT_REPORTS=${expect_reports}" >> "$GITHUB_ENV"
fi

echo "SpotBugs scope: scanning ${kept} changed module(s), skipping ${skipped} unchanged."
echo "Changed modules: ${changed_mods:-<none>}"
if [ -n "${kept_pl}" ]; then
  echo "Reactor scope args: -pl ${kept_pl} -am"
else
  echo "Reactor scope args: <full reactor>"
fi
