package referenceExamples;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;

public class SimplePaintSurface implements Runnable, ActionListener {

    private static final int WIDTH = 1250;
    private static final int HEIGHT = 800;
    private Random random = new Random();
    private JFrame frame = new JFrame("SimplePaintSurface");
    private JPanel tableaux;

    @Override
    public void run() {
        tableaux = new JPanel(null);
        for (int i = 1500; --i >= 0;) {
            addRandom();
        }
        frame.add(tableaux, BorderLayout.CENTER);
        JButton add = new JButton("Add");
        add.addActionListener(this);
        frame.add(add, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tableaux.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        addRandom();
        tableaux.repaint();
    }

    void addRandom() {
        Letter letter = new Letter(Character.toString((char) ('a' + random.nextInt(26))));
        letter.setBounds(random.nextInt(WIDTH), random.nextInt(HEIGHT), 16, 16);
        tableaux.add(letter);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new SimplePaintSurface());
    }
}

