package tictactoe;

public class AIPlayer {

    private static final int[][] WIN_COMBOS = {
        {0,1,2},{3,4,5},{6,7,8},
        {0,3,6},{1,4,7},{2,5,8},
        {0,4,8},{2,4,6}
    };

    public int getBestMove(char[] board, char aiMark, char humanMark) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = aiMark;
                int score = minimax(board, 0, false, aiMark, humanMark);
                board[i] = ' ';
                if (score > bestScore) { bestScore = score; bestMove = i; }
            }
        }
        return bestMove;
    }

    private int minimax(char[] board, int depth, boolean isMaximizing, char aiMark, char humanMark) {
        int result = evaluate(board, aiMark, humanMark);
        if (result != 0) return result;
        boolean full = true;
        for (char c : board) if (c == ' ') { full = false; break; }
        if (full) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = aiMark;
                    best = Math.max(best, minimax(board, depth + 1, false, aiMark, humanMark));
                    board[i] = ' ';
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = humanMark;
                    best = Math.min(best, minimax(board, depth + 1, true, aiMark, humanMark));
                    board[i] = ' ';
                }
            }
            return best;
        }
    }

    private int evaluate(char[] board, char aiMark, char humanMark) {
        for (int[] c : WIN_COMBOS) {
            if (board[c[0]] != ' ' && board[c[0]] == board[c[1]] && board[c[1]] == board[c[2]]) {
                return (board[c[0]] == aiMark) ? 10 : -10;
            }
        }
        return 0;
    }
}
