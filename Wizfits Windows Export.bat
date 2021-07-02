
:: Setup of Script
@echo off
title Wizfits Windows Export

:: Packing into .exe
javapackager nogui

:: Notifying user
echo Successfully created Windows-Executable.
pause & exit