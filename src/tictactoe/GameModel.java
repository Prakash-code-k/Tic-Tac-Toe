package tictactoe;

public class GameModel {

    public enum State { PLAYING, X_WINS, O_WINS, DRAW }

    private char[] board;
    private char currentPlayer;
    private State state;
    private int xWins;
    private int oWins;
    private int draws;

    private static final int[][] WIN_COMBOS = {
        {0,1,2},{3,4,5},{6,7,8},
        {0,3,6},{1,4,7},{2,5,8},
        {0,4,8},{2,4,6}
    };

    public GameModel() {
        xWins = 0;
        oWins = 0;
        draws = 0;
        reset();
    }

    public void reset() {
        board = new char[9];
        for (int i = 0; i < 9; i++) board[i] = ' ';
        currentPlayer = 'X';
        state = State.PLAYING;
    }

    public boolean makeMove(int cell) {
        if (state != State.PLAYING || board[cell] != ' ') return false;
        board[cell] = currentPlayer;
        updateState();
        if (state == State.PLAYING) currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }

    private void updateState() {
        for (int[] combo : WIN_COMBOS) {
            if (board[combo[0]] != ' ' &&
                board[combo[0]] == board[combo[1]] &&
                board[combo[1]] == board[combo[2]]) {
                state = (board[combo[0]] == 'X') ? State.X_WINS : State.O_WINS;
                if (state == State.X_WINS) xWins++;
                else oWins++;
                return;
            }
        }
        boolean full = true;
        for (char c : board) if (c == ' ') { full = false; break; }
        if (full) { state = State.DRAW; draws++; }
    }

    public int[] getWinningCombo() {
        for (int[] combo : WIN_COMBOS) {
            if (board[combo[0]] != ' ' &&
                board[combo[0]] == board[combo[1]] &&
                board[combo[1]] == board[combo[2]]) {
                return combo;
            }
        }
        return null;
    }

    public char getCell(int i) { return board[i]; }
    public char getCurrentPlayer() { return currentPlayer; }
    public State getState() { return state; }
    public int getXWins() { return xWins; }
    public int getOWins() { return oWins; }
    public int getDraws() { return draws; }
}
