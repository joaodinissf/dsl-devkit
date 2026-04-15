# Migration TODO

Items to address during or after the module-by-module migration.

## Source layout

Migrated `jar` modules should move from Eclipse-style `src/` to Maven convention `src/main/java/` (and `src/test/java/` for tests). This makes the layout standard and removes the need for `<sourceDirectory>src</sourceDirectory>` overrides. Do this per-module as part of the migration PR to keep diffs self-contained.

## Dependency version management

Migrated modules currently hardcode dependency versions in each POM. These should be centralized in `ddk-parent`'s `<dependencyManagement>` section (or a BOM) so versions are managed in one place.

## Java version

The `<maven.compiler.release>21</maven.compiler.release>` property should be set once in the parent POM for all jar modules, not repeated per module.
