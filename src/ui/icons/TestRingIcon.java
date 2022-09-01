package ui.icons;

import ui.GameColors;

import javax.swing.*;
import java.awt.*;

public class TestRingIcon implements Icon {

    int diamater;

    public TestRingIcon(int diameter) {
        this.diamater = diameter;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(30));
        g2d.setColor(GameColors.testRing);
        int width = diamater + 2;
        int icon_x = 12;
        g2d.drawArc(icon_x, 0, width, diamater / 2, 0, 360);
        g2d.setColor(GameColors.testRing2);
        g2d.drawArc(icon_x, 2, width, diamater / 2, 0, 360);
        g2d.setColor(GameColors.testRing);
        g2d.drawArc(icon_x, 4, width, diamater / 2, 0, 360);
    }

    @Override
    public int getIconWidth() {
        return diamater;
    }

    @Override
    public int getIconHeight() {
        return diamater;
    }
}
