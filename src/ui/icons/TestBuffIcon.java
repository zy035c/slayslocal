package ui.icons;

import ui.GameColors;

import javax.swing.*;
import java.awt.*;

public class TestBuffIcon implements Icon {

    int diameter;

    public TestBuffIcon(int diameter) {
        this.diameter = diameter;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(GameColors.testBuff3);
        int num = 4;
        int[] points_x = {10, 20, 24, 7};
        int[] points_y = {10, 8, 18, 32};
        g.fillPolygon(points_x, points_y, num);
        int[] points_x2 = {11, 19, 21, 8};
        int[] points_y2 = {12, 10, 18, 29};
        g.setColor(GameColors.testBuff2);
        g.fillPolygon(points_x2, points_y2, num);
        int[] points_x3 = {13, 18, 19, 11};
        int[] points_y3 = {13, 12, 17, 25};
        g.setColor(GameColors.testBuff);
        g.fillPolygon(points_x3, points_y3, num);
    }

    @Override
    public int getIconWidth() {
        return diameter;
    }

    @Override
    public int getIconHeight() {
        return diameter;
    }
}
