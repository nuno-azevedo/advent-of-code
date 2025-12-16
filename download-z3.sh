#!/usr/bin/env bash
set -euo pipefail

BASEDIR=$(cd "$(dirname "${0}")" && pwd)

declare -A Z3_SUFFIX_MAP=(
  ["Darwin arm64"]="arm64-osx-13.7.2"
  ["Darwin x86_64"]="x64-osx-13.7.2"
  ["Linux aarch64"]="arm64-glibc-2.34"
  ["Linux x86_64"]="x64-glibc-2.35"
)

OS_ARCH="$(uname -sm)"
Z3_SUFFIX="${Z3_SUFFIX_MAP[${OS_ARCH}]:-}"

if [[ -z ${Z3_SUFFIX} ]]; then
  echo "[Z3] Unsupported OS/ARCH: ${OS_ARCH}"
  exit 1
fi

Z3_VERSION="4.14.0"
ARCHIVE="z3-${Z3_VERSION}-${Z3_SUFFIX}.zip"
URL="https://github.com/Z3Prover/z3/releases/download/z3-${Z3_VERSION}/${ARCHIVE}"

echo "[Z3] Downloading ${URL}"
TMP="$(mktemp -d)"
trap 'rm -rf "${TMP}"' EXIT
curl --silent --show-error --location "${URL}" -o "${TMP}/${ARCHIVE}"

Z3_DIR="${BASEDIR}/.z3"
unzip -q -j -o "${TMP}/${ARCHIVE}" '*/bin/libz3*' -x '*/bin/libz3.a' -d "$Z3_DIR"
echo "[Z3] Installed Z3 native libs into $Z3_DIR"
