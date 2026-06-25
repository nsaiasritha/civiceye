@echo off
rem CivicEye — Windows Run Script
rem Run AFTER compile.bat

java -cp "bin;lib\sqlite-jdbc.jar" civic.Main
pause
