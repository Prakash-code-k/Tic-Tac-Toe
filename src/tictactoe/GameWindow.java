package tictactoe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {

    private static final Color BG_DARK     = new Color(24, 24, 37);
    private static final Color BG_SURFACE  = new Color(30, 30, 46);
    private static final Color ACCENT      = new Color(203, 166, 247);
    private static final Color TEXT_MUTED  = new Color(166, 173, 200);
    private static final Color COLOR_X     = new Color(243, 139, 168);
    private static final Color COLOR_O     = new Color(137, 180, 250);

    private final GameModel model = new GameModel();
    private final AIPlayer  ai    = new AIPlayer();

    private CellButton[]  cells;
    private JLabel        statusLabel;
    private JLabel        turnIndicator;
    private ScorePanel    scorePanel;
    private JToggleButton aiToggle;
    private boolean       vsAI = false;
    private boolean       aiThinking = false;

    public GameWindow() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(BG_DARK);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);
        root.setBorder(new EmptyBorder(20, 24, 20, 24));

        root.add(buildTopBar(),    BorderLayout.NORTH);
        root.add(buildBoard(),     BorderLayout.CENTER);
        root.add(buildBottomBar(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(0, 8));
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel title = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(ACCENT);

        turnIndicator = new JLabel("X's Turn", SwingConstants.CENTER);
        turnIndicator.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        turnIndicator.setForeground(COLOR_X);

        scorePanel = new ScorePanel();

        top.add(title,         BorderLayout.NORTH);
        top.add(turnIndicator, BorderLayout.CENTER);
        top.add(scorePanel,    BorderLayout.SOUTH);
        return top;
    }

    private JPanel buildBoard() {
        JPanel boardWrapper = new JPanel(new GridBagLayout());
        boardWrapper.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(3, 3, 8, 8));
        grid.setBackground(new Color(17, 17, 27));
        grid.setBorder(BorderFactory.createLineBorder(new Color(49, 50, 68), 2));

        cells = new CellButton[9];
        for (int i = 0; i < 9; i++) {
            final int idx = i;
            cells[i] = new CellButton();
            cells[i].addActionListener(e -> onCellClick(idx));
            grid.add(cells[i]);
        }

        boardWrapper.add(grid);
        return boardWrapper;
    }

    private JPanel buildBottomBar() {
        JPanel bar = new JPanel(new BorderLayout(10, 0));
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(16, 0, 0, 0));

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(ACCENT);
        statusLabel.setPreferredSize(new Dimension(0, 28));

        JButton newGame = styledButton("New Game", BG_SURFACE, ACCENT);
        newGame.addActionListener(e -> resetGame());

        aiToggle = new JToggleButton("vs AI: OFF");
        styleToggle(aiToggle);
        aiToggle.addActionListener(e -> {
            vsAI = aiToggle.isSelected();
            aiToggle.setText(vsAI ? "vs AI: ON" : "vs AI: OFF");
            aiToggle.setForeground(vsAI ? new Color(166, 227, 161) : TEXT_MUTED);
            resetGame();
        });

        JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 0));
        buttons.setOpaque(false);
        buttons.add(newGame);
        buttons.add(aiToggle);

        bar.add(statusLabel, BorderLayout.NORTH);
        bar.add(buttons,     BorderLayout.SOUTH);
        return bar;
    }

    private JButton styledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 40));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(49, 50, 68)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg); }
        });
        return btn;
    }

    private void styleToggle(JToggleButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(BG_SURFACE);
        btn.setForeground(TEXT_MUTED);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 40));
    }

    private void onCellClick(int idx) {
        if (aiThinking) return;
        if (model.getState() != GameModel.State.PLAYING) return;
        if (!model.makeMove(idx)) return;

        cells[idx].setMark(model.getCell(idx) == ' ' ? ' ' : (model.getCell(idx)));

        char placed = 'X';
        for (int i = 0; i < 9; i++) {
            if (model.getCell(i) != ' ') cells[i].setMark(model.getCell(i));
        }

        handleStateChange();

        if (vsAI && model.getState() == GameModel.State.PLAYING) {
            aiThinking = true;
            setTurnIndicator('O');
            Timer delay = new Timer(400, e -> {
                doAIMove();
                aiThinking = false;
            });
            delay.setRepeats(false);
            delay.start();
        }
    }

    private void doAIMove() {
        char[] boardCopy = new char[9];
        for (int i = 0; i < 9; i++) boardCopy[i] = model.getCell(i);
        int best = ai.getBestMove(boardCopy, 'O', 'X');
        if (best >= 0) model.makeMove(best);
        for (int i = 0; i < 9; i++) {
            if (model.getCell(i) != ' ') cells[i].setMark(model.getCell(i));
        }
        handleStateChange();
    }

    private void handleStateChange() {
        GameModel.State state = model.getState();
        scorePanel.update(model.getXWins(), model.getOWins(), model.getDraws());

        if (state == GameModel.State.X_WINS) {
            showWinner("X Wins! 🎉", COLOR_X);
        } else if (state == GameModel.State.O_WINS) {
            showWinner("O Wins! 🎉", COLOR_O);
        } else if (state == GameModel.State.DRAW) {
            statusLabel.setText("It's a Draw! 🤝");
            statusLabel.setForeground(new Color(203, 166, 247));
            turnIndicator.setText("Game Over");
            turnIndicator.setForeground(TEXT_MUTED);
        } else {
            setTurnIndicator(model.getCurrentPlayer());
        }
    }

    private void showWinner(String msg, Color color) {
        int[] combo = model.getWinningCombo();
        if (combo != null) for (int i : combo) cells[i].highlight();
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
        turnIndicator.setText("Game Over");
        turnIndicator.setForeground(TEXT_MUTED);
    }

    private void setTurnIndicator(char player) {
        turnIndicator.setText(player + "'s Turn");
        turnIndicator.setForeground(player == 'X' ? COLOR_X : COLOR_O);
    }

    private void resetGame() {
        model.reset();
        for (CellButton cell : cells) cell.reset();
        statusLabel.setText(" ");
        aiThinking = false;
        setTurnIndicator('X');
    }
}
