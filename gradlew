#!/usr/bin/env sh
##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to locate java executable
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    JAVA_EXEC="$JAVA_HOME/bin/java"
elif type java >/dev/null 2>&1; then
    JAVA_EXEC=java
else
    echo "ERROR: Java not found. Please install Java or set JAVA_HOME." >&2
    exit 1
fi

# Determine the directory of this script
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Execute the Gradle Wrapper jar
exec "$JAVA_EXEC" -cp "$SCRIPT_DIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
