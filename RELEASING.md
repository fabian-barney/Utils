# Releasing

`utils-java` publishes non-`SNAPSHOT` releases to Maven Central through the
Central Publisher Portal. `CHANGELOG.md` is the authoritative release-notes
source used to create and publish the matching GitHub Release.

## Prerequisites

Before the release workflow can succeed:

- the `media.barney` namespace must be verified in the Central Publisher Portal
- a Central Portal user token must be generated for the publishing account
- a GPG key pair must exist and the public key must be published to a supported
  keyserver

## GitHub Secrets

Configure these repository secrets:

- `MAVEN_CENTRAL_TOKEN_USERNAME`: Central Publisher Portal user token username
- `MAVEN_CENTRAL_TOKEN_PASSWORD`: Central Publisher Portal user token password
- `MAVEN_GPG_PRIVATE_KEY`: ASCII-armored private key used to sign artifacts
- `MAVEN_GPG_PASSPHRASE`: passphrase for the private key

## Release Process

1. Make sure the release-ready code is merged into `main` and the working tree
   is clean.
2. Review the delta from the latest release and update the matching entry in
   `CHANGELOG.md`. Scan for stale references with `git grep -F "v<previous>"`.
3. Create an annotated tag using the release version prefixed with `v`, for
   example `v0.1.0`, and push the tag to GitHub.
4. The `Release to Maven Central` workflow derives the Maven version from the
   tag, creates a GitHub Release draft from the matching changelog entry, runs
   `./mvnw -Prelease deploy`, signs the artifacts, and publishes them via the
   Central Publisher Portal.
5. After Maven Central publication succeeds, the workflow promotes the GitHub
   Release draft to a published release. If publication fails, retain the
   draft and follow the immutable-version rollback policy below.

The project keeps `0.0.1-SNAPSHOT` as the default local development version and
overrides `revision` during tagged releases.

## Local Dry Run

You can validate the release profile locally without publishing by running:

```powershell
.\mvnw.cmd -B -ntp -Prelease -Drevision=0.1.0 -Dscm.tag=v0.1.0 -Dcentral.skipPublishing=true deploy
```

This still requires:

- a usable GPG private key in the local keyring

Before tagging, review `THIRD_PARTY_NOTICES.md` and verify that dependency and
transitive-license metadata remains accurate. The published runtime JAR does
not bundle test or build-tool dependencies.

## Publication Targets

Maven Central is the only publication target for this repository. The Gradle
Plugin Portal and private artifact repositories are not applicable.

## Governance

Release preparation must use a focused branch and pull request. Repository
administrators should keep required reviews and status checks enabled on
`main`; release tagging must not be used to bypass those gates.

## Rollback Expectations

Maven Central is immutable. Once a version is published, do not reuse or
overwrite it.

If a release fails before the publish step completes, fix the workflow inputs or
configuration and publish a new version.

If a bad version is already visible on Maven Central, publish a newer version
with the fix and leave the original artifact in place.
