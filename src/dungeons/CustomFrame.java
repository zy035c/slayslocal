package dungeons;

import javax.swing.*;

public class CustomFrame extends JFrame {

    public static int FRAME_WIDTH = 1440;
    public static int FRAME_HEIGHT = 1024;
    public CustomFrame() {
        this.setTitle("Slay The Spire Online Demo");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}
