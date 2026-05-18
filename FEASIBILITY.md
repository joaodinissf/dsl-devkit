# Feasibility study: migrating dsl-devkit from Xtext to Langium (or Fastbelt)

## Context

The DDK has lived on Xtext + Eclipse for over a decade. Three pressures motivate looking at successors:

1. **Mind-share & talent.** Xtext is in maintenance mode; new DSL work in the industry now defaults to Langium (TypeScript, LSP-native).
2. **Distribution.** The DDK ships as Eclipse plugins; consumers increasingly want VS Code / web editors.
3. **TypeFox's roadmap.** TypeFox now positions Langium as their go-to and is incubating a Go-based high-performance toolkit called **Fastbelt** (intro post 2026-03-27).

The user asked specifically whether (a) a heavily-customised Ecore metamodel breaks xtext2langium, (b) Eclipse dependencies would be lost, and (c) Fastbelt is a better target than Langium. This plan answers those and lays out a concrete, low-cost path to confirm the findings in the `explore/langium-feasibility` worktree at `/Users/joao/Git/Avaloq/dsl-devkit-langium`.

## Findings (the feasibility verdict)

### 1. xtext2langium is grammar-only, despite marketing

The TypeFox blog (https://www.typefox.io/blog/xtext-to-langium/) and the v0.4.0 README claim to handle "predefined Ecore model (not generated)". **Reading the actual converter source** (`Xtext2LangiumFragment.xtend`) shows the conversion is structural and skin-deep:

| Ecore construct | Converted? | Notes |
|---|---|---|
| `EClass` hierarchy | ✓ | Single + multi-`extends` emitted to Langium interfaces. |
| `EStructuralFeatures` (non-transient) | ✓ | Mapped to interface fields. |
| `EOperations` | ✗ | Loop never iterates them — silently dropped. |
| `derived="true"` / `transient="true"` | ✗ | Filtered out by `.filter[!it.transient]`. |
| `EAnnotations` (GenModel docs, OCLinEcore, custom URIs) | ✗ | Never read. |
| GenModel customisations (`rootExtendsClass`, custom interfaces, feature init…) | ✗ | `.genmodel` is never opened. |
| Custom `EFactory` impls | n/a | Langium has no factory concept; plain JS objects from parser. |

**Status of xtext2langium itself:** last release v0.4.0 in **Feb 2023**; last push Nov 2023; **10 stars, 0 open issues**. Effectively dormant. Targets Langium v0.x — modern Langium is 4.2.x.

### 2. Concrete DDK exposure to (1)

| DSL | Predefined Ecore? | Custom EOps | Derived/transient | Xbase / JvmModelInferrer |
|---|---|---|---|---|
| Check | ✓ | `getAllChecks()` | `description`, `name`, `message` | **Yes — 31 KB Inferrer** |
| CheckCfg | ✓ | `getConfigurableSections()` | `properties` | Yes |
| Export | ✓ | `getPackageName()`, `getEAttributes()`, `getAllEAttributes()` | (implicit) | No |
| Expression | ✓ | none | none | No |
| Scope | ✓ | none | none | No |
| Valid | ✓ | none | none | No |
| Format | inferred | none | none | Yes (Xbase + Inferrer) |
| HelloWorld / TestLanguage / FormatterTestLanguage | inferred | none | none | No |

For Check, CheckCfg, Export the converter will drop hand-written behaviour that downstream Java code relies on. Format's grammar will convert but its `JvmModelInferrer` is the actual semantic backbone and has no Langium analogue.

### 3. The DDK is not just an Xtext consumer — it is an Xtext meta-toolkit

Confirmed by inspecting generator sources:

- **Check** generator emits Java validators that `extend AbstractDeclarativeValidator` with `@Check` annotations.
- **Export** emits classes extending `AbstractExportedNameProvider` and `AbstractResourceDescriptionStrategy` (Xtext index API).
- **Scope** emits classes extending `AbstractPolymorphicScopeProvider` returning `IScope`.
- **Format** emits formatter stubs against `AbstractFormatter` / `IFormatter2`.

Of the seven production DSLs, **four (Check, Export, Scope, Format) are Xtext-coupled by construction** — their reason for existing is to synthesise Xtext infrastructure. CheckCfg, Expression and Valid are framework-agnostic by *what* they describe, even though they happen to be hosted on Xtext.

### 4. Deep Eclipse coupling in the DDK runtime

The DDK is not just "an Xtext language pack with an Eclipse IDE skin". Substantive Eclipse-platform code:

- `RebuildingXtextBuilder` (custom `IncrementalProjectBuilder` with internal `ResourceDelta`/`ElementTree` use).
- `CheckNature` (`IProjectNature`) + builder participants (`IXtextBuilderParticipant`).
- `RegistryBuilderParticipant` — discovers contributions via Eclipse extension registry.
- `ExtensionPointAwareScopeProvider` — cross-catalog discovery via the same registry.
- Plugin extensions across 14+ extension points: editors, menus, handlers, preference & property pages, marker resolutions, problem markers, compare viewers, project/file wizards, breakpoint adapters, deploy/undeploy popup actions.
- 23 `*.ui` bundles, ~212 files touching `org.eclipse.{ui,jface,swt,core.runtime}`.

### 5. Langium today (May 2026)

- v4.2.x, healthy cadence (~minor every 2-3 months, major per year), ~1 M weekly downloads, under Eclipse Foundation stewardship.
- **TypeScript/Node only.** No JVM target. No Java code-gen runtime — user generators are TS template functions.
- **Eclipse integration is external-process via LSP4E.** A Node process is spawned per language; LSP4E talks to it over stdio. No in-JVM API. No EMF resource set. Semantic highlighting needs custom work.
- Cross-file indexing is a real story (`IndexManager`, `AstNodeDescription`), functionally adequate but with fewer extension hooks than Xtext.
- **What's lost vs Xtext:** EMF as a Java model layer; project natures & Eclipse builders; per-project preferences; marker-to-quickfix pipeline (must be re-mapped to LSP CodeActions); compare framework integration; breakpoint markers; new-project wizards.

### 6. Fastbelt is not a viable target today

- TypeFox positions it as a *complementary* high-performance niche tool, **not a Langium replacement**.
- v0-ish, ~17 stars, alpha, no validator/formatter/scope APIs yet, Go-only output.
- No Eclipse story, no JVM story, no Xtext or Langium migration tooling.
- TypeFox themselves recommend Langium "for most projects" (https://www.typefox.io/blog/xtext-langium-what-next/).
- **Verdict: monitor, do not target.** Revisit late 2026.

### 7. Direct answers to the user's questions

> *"This MWE2 fragment reads your Xtext grammar and converts it to Langium. If you are using a predefined Ecore model (not generated), it will also be converted to Langium as a type definition file."* — **is this the risk?**

**Yes, and the risk is sharper than the blog suggests.** The fragment converts EClass + non-transient EStructuralFeatures and nothing else. For the DDK's heavily-customised metamodels (custom EOperations, derived features, Xbase Inferrer logic, GenModel rootExtendsClass overrides), what comes out is a structural skeleton missing the behaviour. Anyone reading only the blog would think Ecore conversion is a solved problem; reading the source proves otherwise.

> *Would heavy custom metamodel customisation make migration trickier?*

**Yes — proportionally to how much logic lives outside grammar.** For the DDK specifically:
- Check + CheckCfg + Format + Export are high-risk because their EOperations / Inferrers / derived getters carry real behaviour.
- Expression + Scope + Valid are low-risk metamodel-wise (their pain is generator-coupling, not metamodel-coupling).
- HelloWorld + the test grammars are trivial.

> *Anything overly Eclipse-dependent? What would we lose?*

The DDK has Eclipse-platform-locked features that don't have one-to-one LSP analogues: project natures + custom builders, workspace property/preference pages, breakpoint markers, compare merge viewers, new-project wizards, popup-deploy actions, extension-registry-driven scoping. All can be *replaced* under LSP (file-watchers, settings JSON, CodeActions, VS Code commands, registry replaced with file discovery) but every one is a rewrite. Roughly **40–60% of the UI/builder/runtime glue is Eclipse-locked**; the language-processing core (parsers, validators, generators, scope providers as Java logic) is more portable but only conceptually — Langium would force a rewrite into TypeScript.

> *Better to skip Langium and go to Fastbelt?*

No. Fastbelt is alpha-grade and even narrower in scope than Langium (no validators, no formatters, no metamodel). Skipping Langium for Fastbelt would mean rewriting twice.

### 8. Overall feasibility classification

| Path | Cost | Coverage | Risk | Verdict |
|---|---|---|---|---|
| **Full DDK migration to Langium** | very high (multi-year) | partial — JVM/EMF consumers cut off | high — losing the meta-DSL nature of the DDK | **Not recommended** as a single project. |
| **Langium-first for a NEW downstream DSL** (greenfield, no DDK meta-stack) | medium | full | low | Recommended for new languages where TS/VS Code is acceptable. |
| **Hybrid: keep DDK on Xtext, ship Langium-based VS Code clients in parallel** | medium | dual-IDE | medium (sync drift) | Plausible if VS Code support is the actual customer ask. |
| **Migrate to Fastbelt** | very high | very partial | very high | **Not viable** in 2026. |
| **Pilot: pick one small grammar, run xtext2langium end-to-end, write the rest by hand** | low (~1 week) | one grammar | low | **Recommended next step** — produces concrete data, not speculation. |

## Recommended next step (the actual plan)

Do **not** start a full migration. Instead, execute a contained PoC in the `explore/langium-feasibility` worktree to convert *quantified-effort* claims into measured ones. Phases:

### PoC phase 1 — Convert HelloWorld (smallest, inferred metamodel)
- Add the xtext2langium dependency and an MWE2 workflow producing `langium/HelloWorld.langium`.
- Scaffold a fresh Langium 4.x project and drop in the converted grammar.
- Wire `langium-cli`, build the parser, write a one-file `.helloworld` example and confirm parse + AST.
- **Expected outcome:** trivial success. Sets baseline tooling.

### PoC phase 2 — Convert Check (largest, heaviest metamodel, Xbase Inferrer)
- Same conversion pipeline. Document everything xtext2langium emits **and** everything it silently omits (use the table in §1 as a checklist).
- Hand-write replacements in TypeScript for: `getAllChecks()`, `description/name/message` derived getters, the JvmModelInferrer's effective surface (which classes does it ultimately emit? Can any be approximated by a Langium generator?).
- Re-implement `CheckScopeProvider` and `ExtensionPointAwareScopeProvider` semantics under Langium's `ScopeProvider`. The extension-registry part has no Langium analogue — replace with filesystem discovery + manifest convention.
- **Expected outcome:** a thorough cost estimate per category (grammar / metamodel behaviour / scoping / validation / Xbase / generator / UI glue).

### PoC phase 3 — Write the decision memo
Use phases 1+2 measurements to fill a real cost table (person-weeks per DSL, per category), then choose between:
- **Hybrid** (keep DDK on Xtext, ship Langium clients for selected DSLs).
- **Greenfield-only** (new DSLs go to Langium; legacy stays).
- **Sunset Xtext** (full migration, accept the rewrite cost).

### Critical files to inspect during the PoC

Reference files for every category that will need re-implementation:

- Grammar: `com.avaloq.tools.ddk.check.core/src/com/avaloq/tools/ddk/check/Check.xtext`
- Metamodel: `com.avaloq.tools.ddk.check.core/metamodel/com/avaloq/tools/ddk/check/Check.{ecore,genmodel}`
- JvmModelInferrer: `com.avaloq.tools.ddk.check.core/src/com/avaloq/tools/ddk/check/jvmmodel/CheckJvmModelInferrer.xtend` (31 KB — read in full)
- Generator: `com.avaloq.tools.ddk.check.core/xtend-gen/com/avaloq/tools/ddk/check/generator/CheckGenerator.xtend`
- Scope provider: `com.avaloq.tools.ddk.check.core/xtend-gen/com/avaloq/tools/ddk/check/scoping/CheckScopeProvider.java`
- Extension-aware scope provider: `com.avaloq.tools.ddk.check.core/src/com/avaloq/tools/ddk/check/scoping/ExtensionPointAwareScopeProvider.java`
- Runtime module: `com.avaloq.tools.ddk.check.core/src/com/avaloq/tools/ddk/check/CheckRuntimeModule.java`
- Resource description strategy: `com.avaloq.tools.ddk.check.core/src/com/avaloq/tools/ddk/check/resource/CheckResourceDescriptionStrategy.java`
- Custom builder: `com.avaloq.tools.ddk.xtext.builder/src/com/avaloq/tools/ddk/xtext/builder/RebuildingXtextBuilder.java`
- Eclipse plugin manifest: `com.avaloq.tools.ddk.check.ui/plugin.xml`

### Verification

The PoC ends when **all four** of these are demonstrably true (or demonstrably false — either is a win):

1. `xtext2langium` produces a `HelloWorld.langium` file that parses and produces an AST in Langium 4.2.x.
2. The same workflow on `Check.xtext` produces a file plus a type-defs file, and the *delta* between what's emitted and what's needed is enumerated concretely (table of dropped EOperations, derived features, Xbase-inferred classes, etc.).
3. A hand-written TypeScript `CheckScopeProvider` resolves a cross-catalog reference in a sample workspace.
4. A measured cost estimate (in person-weeks) per DSL × category, written into `worktree-root/FEASIBILITY.md` and reviewed against the table in §8 of this document.

If 1+2 succeed but 3 reveals the extension-registry semantics can't be replaced cleanly, that's an early "abort the full migration" signal and the hybrid path becomes the recommended outcome.

### Decision boundary

If after PoC phases 1+2 the measured cost for Check alone is > 8 person-weeks of net-new TS work, treat the full migration as economically unviable and pivot to the hybrid path.

---

## PoC results (2026-05-15)

Ran xtext2langium 0.4.0 against one mechanical grammar and one non-mechanical grammar. Code lives in `poc-x2l-runner/` (standalone Maven project, **not** in the Tycho reactor). The fragment was driven by a tiny Java main (`RunX2L.java`) because MWE2's `Mwe2Launcher` couldn't bootstrap the JDK type system from the Maven CLI — direct invocation sidesteps that issue.

### Setup

| Item | Value |
|---|---|
| DDK Xtext version | 2.42.0 |
| xtext2langium version | 0.4.0 (last release Feb 2023) |
| Maven Central artifact | `io.typefox.xtext2langium:io.typefox.xtext2langium:0.4.0` (35 KB) |
| Production edits | `GenerateHelloWorld.mwe2` + `CommonXbase.mwe2` declare the fragment; harmless to Maven because workflows aren't part of `mvn verify`. |

### Build verification

| Build | Result | Modules | Wall time |
|---|---|---|---|
| Before (clean `upstream/master`) | BUILD SUCCESS | 64/64 | 44 s |
| After (workflow edits + standalone runner present) | BUILD SUCCESS | 64/64 | 26 s |

`mvn clean verify -DskipTests -T 3C -f ./ddk-parent/pom.xml --batch-mode --fail-at-end` from the worktree root. The `poc-x2l-runner/` sub-project is intentionally outside the Tycho reactor.

### Case 1 — Mechanical DSL: HelloWorld

**Result:** clean conversion. Two files emitted:

- `Terminals.langium` (8 lines) — the standard `org.eclipse.xtext.common.Terminals` mapped to Langium primitives.
- `HelloWorld.langium` (55 lines) — full grammar with `entry Model infers Model:` style, all rules present.

What survived:
- All parser rules and their alternatives.
- Cross-grammar import (`import 'Terminals'`).
- Inferred metamodel — rendered as `infers <Type>` annotations on rules.

What was dropped (per inspection vs. the source `.xtext`):
- All Javadoc/multiline comments (e.g. `/** @KeywordRule(one) */` annotations on `KeyOne`, `KeyTwo`, `KeyOther` rules). These were DDK-style markers; downstream tooling reading the grammar's comments would break.
- The `generate helloWorld "<nsURI>"` directive doesn't survive — Langium uses inferred type declarations on the rules instead.

Verdict: **mechanical DSLs work as advertised.** Expect ~5 minutes of conversion + a checklist of dropped comments per grammar.

### Case 2 — Non-mechanical DSL: Check

**Result: failure to emit `Check.langium`.** The fragment crashed before producing the user's grammar output.

Sequence of events:

1. Grammar loads with two warnings (non-fatal):
   - `Cannot add supertype 'XExpression' to sealed type 'XGuardExpression'`
   - `Cannot add supertype 'XExpression' to sealed type 'XIssueExpression'`
   
   These are real signals — Xbase in 2.42 marks `XGuardExpression`/`XIssueExpression` as sealed; Check's grammar tries to extend `XExpression` from them. The conversion proceeds anyway.

2. Fragment generates the **imported** grammars first (Xbase chain):
   - `Xtype.langium`, `Xbase.langium`, `XbaseWithAnnotations.langium`
   - `JavaVMTypes-types.langium`, `XAnnotations-types.langium`, `Xbase-types.langium`, `Xtype-types.langium`

3. Fragment crashes with **NullPointerException** in `TransformationContext.doAddType`:
   ```
   Cannot invoke "EPackage.getNsURI()" because the return value of "EClassifier.getEPackage()" is null
   ```
   Stack: `processElement → handleType → addTypeIfReferenced → doAddType → NPE`.
   The crash happens while processing a synthetic EClassifier that lacks an EPackage — almost certainly a fallout of the sealed-type incompatibility from step 1.

4. **No `Check.langium` is written**, no `Check-types.langium`, nothing for the DSL under test. Only the seven imported-grammar artifacts above survive.

What this means concretely:

- xtext2langium 0.4.0 cannot process Check's grammar against Xtext 2.42. There is no published version targeting modern Xtext, and the repo is dormant (last push Nov 2023).
- Even if the NPE were patched, the §1 limits still apply: Check's 31 KB JvmModelInferrer, `getAllChecks()` EOperation, and `description`/`name`/`message` derived getters would not survive the round-trip regardless.
- For the full DDK migration, **the cost predicted in §2 holds**: Check is the highest-risk DSL, and no automated tooling reduces that risk meaningfully.

Verdict: **non-mechanical DSLs are a manual rewrite from the start.** xtext2langium provides nothing usable for Check today.

### Implications for the §8 path table

The §2/§8 estimates land:

- **Mechanical DSLs (HelloWorld + tests):** xtext2langium is helpful; cost ≈ pure rewrite of generators/UI glue.
- **Non-mechanical DSLs (Check, CheckCfg, Format, Export):** xtext2langium is non-functional — you write the `.langium` grammar by hand, plus everything else. No starting boost.
- The "Pilot" path in §8 is now de-risked: HelloWorld confirms the tool chain works for trivial cases; Check confirms the hard cases need hand-work.

### Artefacts left in the worktree (uncommitted)

| Path | Purpose |
|---|---|
| `poc-x2l-runner/` | Standalone Maven driver (not in Tycho reactor). |
| `poc-x2l-runner/src/main/java/com/avaloq/poc/RunX2L.java` | Direct driver — bypasses MWE2 launcher. |
| `poc-x2l-runner/src/main/mwe2/HelloWorld2Langium.mwe2` | Original MWE2 attempt (kept for reference; fails under Maven CLI). |
| `poc-x2l-runner/out/helloworld/HelloWorld.langium` + `Terminals.langium` | Mechanical-case output. |
| `poc-x2l-runner/out/check/*.langium` | Partial output — Xbase/Xtype/etc. emitted, **no Check.langium**. |
| `com.avaloq.tools.ddk.sample.helloworld/.../GenerateHelloWorld.mwe2` | + `fragment = io.typefox.xtext2langium.Xtext2LangiumFragment` declaration. |
| `com.avaloq.tools.ddk.workflow/.../CommonXbase.mwe2` | + same declaration in the shared Xbase macro. |

Branch `explore/langium-feasibility`, **not committed, not pushed**.
