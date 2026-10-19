# Signing keys

`dev.p12` is a **public** development key (alias and password: `codeg-dev`).
Release builds use it whenever no `keystore.properties` is present, so every CI
APK has the same signature and installs over the previous one.

Because it is public, anyone with this repository could sign an APK that
upgrades over yours. That is fine for a personal, sideloaded build. For
builds you distribute, sign with a private key instead: the Release workflow
reads it from the `ANDROID_RELEASE_*` repository secrets. Switching keys
requires uninstalling the app once.
