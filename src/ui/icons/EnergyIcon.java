package ui.icons;

import ui.GameColors;

import javax.swing.*;
import java.awt.*;

public class EnergyIcon implements Icon {

    int width;
    int height;

    public EnergyIcon(int diameter) {
        this.width = diameter;
        this.height = diameter;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int energyBorderWidth = width / 6;

        g.setColor(GameColors.energyBorder1);
        g.drawArc(0, 0, width, height, 0, 360);
        g.setColor(GameColors.energyBorder2);
        g.fillArc(0, 0, width, height, 0, 360);
        g.setColor(GameColors.energyBorder3);
        g.fillArc(
                energyBorderWidth / 2,
                energyBorderWidth / 2,
                width - energyBorderWidth,
                width - energyBorderWidth,
                0,
                360
        );
        g.setColor(GameColors.energy4);
        g.fillArc(
                (int)(energyBorderWidth),
                (int)(energyBorderWidth),
                width - 2 * energyBorderWidth,
                height - 2 * energyBorderWidth,
                0,
                360
        );
    }

    @Override
    public int getIconWidth() {return width;}
    @Override
    public int getIconHeight() {return height;}
}
