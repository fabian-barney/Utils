# Changelog

All notable releases of `utils-java` are documented here. The release workflow
uses each versioned section as the authoritative GitHub Release notes.

## [0.0.11] - 2026-08-31

### Changed

- clarified that the `quality-gate-*` Maven commands run the full Maven
  lifecycle through the `verify` phase
- upgraded the CRAP quality plugin to 0.6.2 and the cognitive-complexity
  quality plugin to 0.7.0
- upgraded the build-time NullAway analysis tool to 0.14.0
- upgraded the GitHub Actions Java setup action to 6.0.0 in CI and release
  automation

### Compatibility

- no public API or runtime behavior changes
- the Java 21 release baseline remains unchanged

## [0.0.10] - 2026-08-23

### Changed

- updated the JUnit Jupiter test dependency to 6.1.3

### Compatibility

- no public API or runtime behavior changes

## [0.0.9] - 2026-08-09

### Changed

- aligned Maven Central deployment naming with the repository coordinates
- hardened the tag-release signing preflight by verifying that a secret key was
  imported
- updated the GitHub Actions checkout and setup-java versions used by CI and
  release automation
- added automated GitHub Release draft creation and post-publication promotion

### Compatibility

- no public API or runtime behavior changes

## [0.0.8] - 2026-08-08

### Changed

- updated the JSpecify dependency to 1.0.1
