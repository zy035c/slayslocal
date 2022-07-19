package referenceExamples;

import javax.swing.*;
import java.awt.*;

class InfoBoxComponent extends JComponent {
    public static final int MESSAGE_X = 75;
    private static final int MESSAGE_Y = 100;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public void paintComponent(Graphics g) {
        g.drawString("InfoBoxProgram!", MESSAGE_X, MESSAGE_Y);
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_WIDTH);
    }
}