# Changelog

All notable releases of `utils-java` are documented here. The release workflow
uses each versioned section as the authoritative GitHub Release notes.

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
