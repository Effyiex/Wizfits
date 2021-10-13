
@echo off
title WizfitsClient-Compiler

cd source
echo Compiling the client...
powershell -nop -c "& {sleep -m 512}"
"%JAVA_HOME%/javac" -source 8 -target 8 *.java
cd ..

echo Moving compiled files...
powershell -nop -c "& {sleep -m 512}"
for /r "source" %%f in (*.class) do move "%%f" "compiled" > NUL

echo Packing compiled files...
powershell -nop -c "& {sleep -m 512}"
"%JAVA_HOME%/jar" cfe "Wizfits Client.jar" WizfitsClient -C "compiled" . > NUL

echo Executing Debug-Session...
powershell -nop -c "& {sleep -m 512}"
"%JAVA_HOME%/java" -jar "Wizfits Client.jar"
echo Debug-Session has ended.
