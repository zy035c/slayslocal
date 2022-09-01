package referenceExamples;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;


public class Swing extends JFrame {

    JTextArea area;
    public Swing() {
        initUI();
    }

    public final void initUI() {

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);

        area = new JTextArea();
        area.setBounds(10, 60, 80, 30);
        panel.add(area);

        JButton jButton = new JButton("Click");
        jButton.setBounds(90, 60, 80, 30);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                delayedAction();
            }
        });

        panel.add(jButton);

        setTitle("Quit button");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        Swing ex = new Swing();
        ex.setVisible(true);

    }

    public void delayedAction(){
        String pin = area.getText();
        String msg[] = {"Cracking the database...\n","Database Cracked succesfully!\n","Running the exploit...\n","Passing '"+pin+"'  to the exploit..\n","New code succesfully spoofed!\n"};
        int time[] = {2000,1400,1000,2500};
        int x=0;
        long k=2000;

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO add your handling code here:
                //PSCGenerator ps = new PSCGenerator();

                area.append(msg[x]);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
