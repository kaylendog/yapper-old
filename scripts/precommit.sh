#!/usr/bin/env bash
set -euo pipefail
# run spotless
echo + ./gradlew --console=plain -w spotlessApply spotlessCheck
./gradlew --console=plain -w spotlessApply spotlessCheck
# iterate over already staged files and re-add
for file in $(git diff --name-only --cached)
do
  echo + git add $file
  git add $file
done
