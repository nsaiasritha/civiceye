#!/bin/bash
# CivicEye — Compile Script
# Run this from the CivicEye project root directory.

JAVAC=$(which javac 2>/dev/null)
if [ -z "$JAVAC" ]; then
    echo "ERROR: javac not found. Please install JDK 11+ and ensure it is on your PATH."
    exit 1
fi

echo "Compiling CivicEye..."
mkdir -p bin/civic

javac -cp "lib/sqlite-jdbc.jar" -d bin src/civic/*.java
if [ $? -eq 0 ]; then
    echo "Build successful!"
else
    echo "Build FAILED. Fix the errors above and try again."
    exit 1
fi
