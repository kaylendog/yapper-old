#!/usr/bin/env bash
set -euxo pipefail
# run spotless
./gradlew spotlessApply spotlessCheck
