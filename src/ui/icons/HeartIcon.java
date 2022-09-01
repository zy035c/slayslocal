package ui.icons;

import ui.GameColors;

import javax.swing.*;
import java.awt.*;

public class HeartIcon implements Icon {

    int width;
    int height;

    public HeartIcon(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(GameColors.heart);
        int[] triangleX = {
                x - 2*width/18,
                x + width + 2*width/18,
                (x - 2*width/18 + x + width + 2*width/18)/2};
        int[] triangleY = {
                y + height - 2*height/3,
                y + height - 2*height/3,
                y + height };
        g.fillOval(
                x - width/12,
                y,
                width/2 + width/6,
                height/2);
        g.fillOval(
                x + width/2 - width/12,
                y,
                width/2 + width/6,
                height/2);
        g.fillPolygon(triangleX, triangleY, triangleX.length);
    }

    @Override
    public int getIconWidth() {
        return this.width;
    }

    @Override
    public int getIconHeight() {
        return this.height;
    }
}
