package referenceExamples;

import javax.swing.*;
import java.awt.*;

public class InfoBox {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new InfoBoxFrame();
            frame.setTitle("InfoBox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
