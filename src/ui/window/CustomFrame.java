package ui.window;

import javax.swing.*;

public class CustomFrame extends JFrame {

    // 1440 x 1024
    // 1280 x 800
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 800;
    public CustomFrame() {
        this.setTitle("Dark Slay™ Local Demo");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

    }



}
