package tictactoe;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private static final Color BG         = new Color(24, 24, 37);
    private static final Color COLOR_X    = new Color(243, 139, 168);
    private static final Color COLOR_O    = new Color(137, 180, 250);
    private static final Color COLOR_DRAW = new Color(203, 166, 247);

    private final JLabel xLabel;
    private final JLabel oLabel;
    private final JLabel drawLabel;

    public ScorePanel() {
        setLayout(new GridLayout(1, 3, 12, 0));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        xLabel    = makeScore("X", "0", COLOR_X);
        drawLabel = makeScore("DRAW", "0", COLOR_DRAW);
        oLabel    = makeScore("O", "0", COLOR_O);

        add(xLabel);
        add(drawLabel);
        add(oLabel);
    }

    private JLabel makeScore(String label, String val, Color color) {
        JLabel lbl = new JLabel("<html><center><span style='font-size:11px;color:#a6adc8'>"
            + label + "</span><br><span style='font-size:26px;font-weight:bold;color:"
            + toHex(color) + "'>" + val + "</span></center></html>", SwingConstants.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(30, 30, 46));
        lbl.setBorder(BorderFactory.createLineBorder(new Color(49, 50, 68), 1));
        return lbl;
    }

    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    public void update(int x, int o, int d) {
        xLabel.setText(scoreHtml("X", x, new Color(243, 139, 168)));
        oLabel.setText(scoreHtml("O", o, new Color(137, 180, 250)));
        drawLabel.setText(scoreHtml("DRAW", d, new Color(203, 166, 247)));
    }

    private String scoreHtml(String label, int val, Color color) {
        return "<html><center><span style='font-size:11px;color:#a6adc8'>"
            + label + "</span><br><span style='font-size:26px;font-weight:bold;color:"
            + toHex(color) + "'>" + val + "</span></center></html>";
    }
}
