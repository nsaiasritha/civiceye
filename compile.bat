@echo off
rem CivicEye — Windows Compile Script

if not exist bin\civic mkdir bin\civic

javac -cp "lib\sqlite-jdbc.jar" -d bin src\civic\*.java
if %ERRORLEVEL% == 0 (
    echo Build successful!
) else (
    echo Build FAILED.
)
pause
