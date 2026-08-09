# Third-Party Notices

The published `utils-java` runtime JAR contains only project classes and does
not bundle third-party runtime code.

## Provided dependency

- `org.jspecify:jspecify:1.0.1` — Apache License 2.0. The dependency is declared
  with Maven `provided` scope. See the [JSpecify project](https://jspecify.dev/)
  and its [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0).

## Test and build dependencies

JUnit, Maven plugins, static-analysis tools, and other build-time dependencies
are used only to build and verify this project. They are not bundled in the
published runtime JAR or source distribution.
