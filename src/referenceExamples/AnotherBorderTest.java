package referenceExamples;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class AnotherBorderTest extends JFrame {
    public AnotherBorderTest() {
        setTitle("Border Test");
        setSize(450, 450);

        JPanel content = (JPanel) getContentPane();
        content.setLayout(new GridLayout(6, 2, 3, 3));

        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.RAISED));
        p.add(new JLabel("RAISED BevelBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.LOWERED));
        p.add(new JLabel("LOWERED BevelBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new LineBorder(Color.black, 5));
        p.add(new JLabel("Black LineBorder, thickness = 5"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.add(new JLabel("EmptyBorder with thickness of 10"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        p.add(new JLabel("RAISED EtchedBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        p.add(new JLabel("LOWERED EtchedBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        p.add(new JLabel("RAISED SoftBevelBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        p.add(new JLabel("LOWERED SoftBevelBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new MatteBorder(new ImageIcon("BALL.GIF")));
        p.add(new JLabel("MatteBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new TitledBorder(new MatteBorder(
                new ImageIcon("java2sLogo.gif")), "Title String"));
        p.add(new JLabel("TitledBorder using MatteBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new TitledBorder(new LineBorder(Color.black, 5),
                "Title String"));
        p.add(new JLabel("TitledBorder using LineBorder"));
        content.add(p);

        p = new JPanel();
        p.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10),
                "Title String"));
        p.add(new JLabel("TitledBorder using EmptyBorder"));
        content.add(p);

        setVisible(true);
    }

    public static void main(String args[]) {
        new AnotherBorderTest();
    }
}