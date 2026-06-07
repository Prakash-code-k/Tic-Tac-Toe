@echo off
echo Compiling Tic-Tac-Toe...
if not exist out mkdir out
setlocal enabledelayedexpansion
set SOURCES=
for /r src %%f in (*.java) do set SOURCES=!SOURCES! "%%f"
javac -d out %SOURCES%
if %errorlevel% == 0 (
    echo Launching...
    java -cp out tictactoe.Main
) else (
    echo Compilation failed. Make sure JDK 17+ is installed.
    pause
)
