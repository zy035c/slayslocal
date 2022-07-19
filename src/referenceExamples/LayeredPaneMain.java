package referenceExamples;

import ui.GameColors;

import javax.swing.*;
import java.awt.*;

public class LayeredPaneMain {

    public static void main(String[] args) {

        // JLayeredPane = Swing container that provides a
        //				third dimension for positioning components
        //				ex. depth, Z-index

        JLabel label1= new JLabel();
        label1.setOpaque(true);
        label1.setBackground(Color.RED);
        label1.setBounds(50,50,200,200);
        label1.setLayout(new FlowLayout());

        JLabel label2= new JLabel();
        label2.setOpaque(true);
        label2.setBackground(Color.GREEN);
        label2.setBounds(100,100,200,200);

        JLabel label3= new JLabel();
        label3.setOpaque(true);
        label3.setBackground(Color.BLUE);
        label3.setSize(200,200);
        label3.setLocation(150,150);
        //label3.setText("text");
        // label3.setBounds(150,150,200,200);
        label3.setLayout(new FlowLayout());

        JTextArea description = new JTextArea();
        description.setText("this.card.DESCRIPTION");
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        label1.add(description);
        description.setPreferredSize(new Dimension(100, 100));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,500,500);
        layeredPane.setBackground(GameColors.grey1);
        layeredPane.setOpaque(true);

        //layeredPane.add(label1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(label1);
        layeredPane.add(label2);
        layeredPane.add(label3);
        // layeredPane.add(description);

        JFrame frame = new JFrame("JLayeredPane");
        frame.add(layeredPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
