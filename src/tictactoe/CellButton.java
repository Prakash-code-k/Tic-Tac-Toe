package tictactoe;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {

    private static final Color BG_NORMAL   = new Color(30, 30, 46);
    private static final Color BG_HOVER    = new Color(49, 50, 68);
    private static final Color BG_WIN      = new Color(166, 227, 161);
    private static final Color COLOR_X     = new Color(243, 139, 168);
    private static final Color COLOR_O     = new Color(137, 180, 250);
    private static final Color COLOR_WIN   = new Color(30, 30, 46);

    private boolean highlighted = false;

    public CellButton() {
        setPreferredSize(new Dimension(140, 140));
        setFont(new Font("Segoe UI", Font.BOLD, 64));
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(BG_NORMAL);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(true);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!highlighted && getText().isEmpty()) setBackground(BG_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!highlighted) setBackground(BG_NORMAL);
            }
        });
    }

    public void setMark(char mark) {
        if (mark == 'X') {
            setText("X");
            setForeground(COLOR_X);
        } else if (mark == 'O') {
            setText("O");
            setForeground(COLOR_O);
        } else {
            setText("");
        }
        highlighted = false;
        setBackground(BG_NORMAL);
    }

    public void highlight() {
        highlighted = true;
        setBackground(BG_WIN);
        setForeground(COLOR_WIN);
    }

    public void reset() {
        setText("");
        highlighted = false;
        setBackground(BG_NORMAL);
    }
}
