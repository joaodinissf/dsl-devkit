**Nx Maven Integration Guide**

- **Overview**  
  Integrate the dependency resolver and Maven builds with Nx so that each Avaloq bundle becomes an Nx project, Maven runs in parallel, and outputs get cached in `.m2`. This guide assumes:
  - `dependency_resolver.py` produces the module list for a bundle.
  - `generate_build_scripts.py` and `validate_build_scripts.py` create and validate per-bundle build scripts.
  - `.mvn/maven.config` already points Maven at `.m2/repository`.

- **Prerequisites**  
  Node.js (>=18), Nx CLI (`npm install -g nx` or `npx nx`), Python/`uv`, GNU `parallel`, plus the existing Maven/Tycho setup.

- **1. Standardize Dependency Command Output**
  1. Generate scripts from the resolver: `uv run --with tqdm python3 generate_build_scripts.py --jobs 8`.
  2. Verify each `build-scripts/<bundle>.sh` contains one `mvn … -pl …` command.
  3. Optionally trim scripts to a single command line for consistency.

- **2. Scaffold Nx Projects for Bundles**
  1. Initialize Nx if missing: `npx nx init`.
  2. For every generated script, add an Nx project entry (run-commands executor) that invokes the script and declares `.m2/repository` as an output. Automate the `workspace.json` edits via a helper script.

- **3. Wire Dependency Graph into Nx**
  1. Extend the generator to emit dependency metadata (JSON map from bundle to required bundles).
  2. Inject `implicitDependencies` in each Nx project so the build graph respects resolver output.

- **4. Configure Maven Cache Awareness**
  1. Ensure each Nx target lists `.m2/repository` (or subpaths) in `outputs`.
  2. If using Nx Cloud, configure `.nx/executors.json` so remote cache stores those directories.

- **5. Add Aggregate Targets**
  1. Define a meta project (e.g., `ddk-build`) that depends on every bundle project.
  2. Hook `validate_build_scripts.py` in a `verify` target (dependsOn `^build`) to check all scripts.

- **6. Optional Nx Generator**
  Write a custom Nx generator (`tools/generators/bundle-build`) that reruns the Python scripts, refreshes `workspace.json`, and commits updates. Invoked via `nx g bundle-build`.

- **7. Usage Examples**
  - `npx nx run com-avaloq-tools-ddk-checkcfg-ui:build`
  - `npx nx run-many --target=build --projects com-avaloq-tools-ddk-*`
  - `npx nx run ddk-build:verify`

- **8. Troubleshooting Tips**
  - Cache misses? Verify `.m2/repository` is untouched between runs and remains the Maven repo location.
  - Feature projects (`feature.xml` only) lack manifests; exclude or model them as separate Nx libraries.
  - Bundles skipped by the resolver (missing `pom.xml`) need Tycho metadata or explicit exclusion from Nx scaffolding.
  - Regenerate scripts/Nx metadata on CI for reproducibility.
