#!/usr/bin/env bash
set -euxo pipefail
# run spotless
./gradlew --console=plain -w spotlessApply spotlessCheck
