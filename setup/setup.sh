#!/bin/bash

# ./setup install absolute_path
# ./setup repair
# ./setup uninstall

usage() {
	echo "Usage: <command> [<absolute_path>]"
	echo ""
	echo "Make sure that the current directory contains the IdLearn.jar file and does NOT contain a directory called 'mimuw'."
	echo ""
	echo "Available commands:"
	echo "    install <absolute_path>       installs IdLearn's files in <absolute_path>"
	echo "    repair                        attempts to repair broken IdLearn's files in a installation directory"
	echo "    uninstall                     removes IdLearn's files"
}

get_install_path() {
	jar xf "${PACKAGE}" "${CONFIG}"
	TMP=$(cat "$CONFIG")
	IFS='=' read -ra TAB <<< "$TMP"
	TMP=""
	for i in "${TAB[@]}"; do
		TMP="${i}"
	done
    rm -rf mimuw
	echo "${TMP}"
}

build_packages() {
	jar xf "${PACKAGE}" "${PROBLEMS_DIR}"
	mv "${PROBLEMS_DIR}" "${INSTALL_PATH}"
	cd "${INSTALL_PATH}/problems" || exit 1
	for i in */ ; do
		echo "Building $i"
		cd "${i}" || exit 1
		make all
		cd ".."
	done
	cd "${SETUP_DIR}" || exit 1
	rm -rf mimuw
}

if [[ "$#" -lt 1 ]]; then
	usage
	exit 1
fi

CMD=$1

PACKAGE="./IdLearn.jar"
TMP_MIMUW="./mimuw"
SETUP_DIR=$(pwd)
CONFIG_DIR="mimuw/idlearn/properties"
PROBLEMS_DIR="mimuw/idlearn/packages/problems/"
CONFIG="${CONFIG_DIR}/application.properties"
if [ ! -f "$PACKAGE" ]; then
    echo "Error: Cannot install IdLearn: $PACKAGE does not exist"
    usage
    exit 1
fi

if [ -d "${TMP_MIMUW}" ]; then
    echo "Error: Cannot install IdLearn: ${TMP_MIMUW} exists in current directory"
    usage
    exit 1
fi

if [[ "$CMD" == "install" ]]; then
	if [[ "$#" -ne 2 ]]; then
		usage
        exit 1
    fi

    INSTALL_PATH="$2"
	rm -rf $INSTALL_PATH
    mkdir -p "${INSTALL_PATH}"

    mkdir -p "${CONFIG_DIR}"
    touch "${CONFIG}"
    echo "data=$INSTALL_PATH" > $CONFIG
    jar uf "${PACKAGE}" "${CONFIG}"

    build_packages
elif [[ "$CMD" == "uninstall" ]]; then
	INSTALL_PATH="$(get_install_path)"
	rm -rf $INSTALL_PATH
elif [[ "$CMD" == "repair" ]]; then
	INSTALL_PATH="$(get_install_path)"
	rm -rf $INSTALL_PATH/problems
	build_packages
else
	usage
fi
