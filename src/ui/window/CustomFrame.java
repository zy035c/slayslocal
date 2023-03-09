package ui.window;

import ui.GameColors;

import javax.swing.*;
import java.awt.*;

public class CustomFrame extends JFrame {

    // 1440 x 1024
    // 1280 x 800
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 800;
    public JLayeredPane layeredPane;
    public CustomFrame() {
        this.setTitle("Dark Slay Java Demo");
        Dimension size = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);

        this.setSize(size);
        this.setMaximumSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        layeredPane = new JLayeredPane();
        layeredPane.setLocation(0, 0);
        layeredPane.setSize(size);
        layeredPane.setMaximumSize(size);
        layeredPane.setMaximumSize(size);
        layeredPane.setPreferredSize(size);
        layeredPane.setLayout(null);
        layeredPane.setBackground(GameColors.gameBackground);
        layeredPane.setEnabled(true);
        layeredPane.setVisible(true);
        layeredPane.setOpaque(true);

        this.add(layeredPane);
    }

    public void addComp(Component component, int layer) {
        layeredPane.add(component, layer);
    }

}
