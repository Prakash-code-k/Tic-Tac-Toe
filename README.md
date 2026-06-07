# Tic-Tac-Toe — Java Swing GUI

A clean, dark-themed Tic-Tac-Toe game built with Java Swing.

## Features
- Two-player mode (local)
- vs AI mode using the Minimax algorithm (unbeatable)
- Score tracking across rounds (X wins / O wins / Draws)
- Winning cells highlighted in green
- Hover effects on board cells
- "New Game" resets the board, scores persist
- Toggle AI on/off at any time

## Project Structure
```
TicTacToe/
├── src/tictactoe/
│   ├── Main.java          ← Entry point
│   ├── GameModel.java     ← Board state, win detection
│   ├── AIPlayer.java      ← Minimax AI
│   ├── GameWindow.java    ← Main GUI window
│   ├── CellButton.java    ← Board cell component
│   └── ScorePanel.java    ← Score display
├── run.sh
└── run.bat
```

## How to Run

Requires **JDK 17+**

### Linux / macOS
```bash
chmod +x run.sh
./run.sh
```

### Windows
```
run.bat
```

### Manual
```bash
mkdir out
find src -name "*.java" | xargs javac -d out
java -cp out tictactoe.Main
```
