@echo off
set DIR=%~dp0
set JAVA_EXEC=java
if defined JAVA_HOME (
  set JAVA_EXEC=%JAVA_HOME%\bin\java
)
"%JAVA_EXEC%" -cp "%DIR%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
