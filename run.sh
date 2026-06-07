#!/bin/bash
echo "Compiling Tic-Tac-Toe..."
mkdir -p out
find src -name "*.java" | xargs javac -d out
if [ $? -eq 0 ]; then
    echo "Launching..."
    java -cp out tictactoe.Main
else
    echo "Compilation failed. Make sure JDK 17+ is installed."
fi
