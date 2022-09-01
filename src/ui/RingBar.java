package ui;

import core.AbstractPlayer;
import rings.AbstractRing;
import ui.icons.TestRingIcon;
import ui.tooltips.GameToolTip;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

import static ui.GUI.calcX;

public class RingBar extends JLayeredPane {

    private static final int RING_SEPARATION = calcX(5);

    public ArrayList<RingLabel> ringLabels;
    AbstractPlayer player;
    // 透明的component
    public RingBar(AbstractPlayer player, int x, int y) {
        super();
        this.player = player;
        ringLabels = new ArrayList<>();

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(boxLayout);
        this.setEnabled(true);
        this.setBounds(
                x,
                y,
                500,
                RING_HEIGHT + calcX(2)
        );
    }

    private final int RING_WIDTH = calcX(50);
    private final int RING_HEIGHT = RING_WIDTH;

    public void inBattleUpdate() {
        if (ringLabels.isEmpty()) {
            if (player.rings.isEmpty()) {
                return;
            }
            for (AbstractRing ring: this.player.rings) {
                RingLabel label = new RingLabel(ring);
                this.add(label);
                this.add(Box.createRigidArea(new Dimension(RING_SEPARATION, 10)));
                System.out.println("Updating "+this.player.name+" ring. Ring size = "+this.player.rings.size());

                this.ringLabels.add(label);
                label.update();
            }
        } else {
            for (RingLabel label: this.ringLabels) {
                if (!label.ring.getAmountString().equals(label.ringBorder.getTitle())) {
                    label.update();
                }
            }
        }
        assert ringLabels.size() == this.player.rings.size();
        repaint();
        revalidate();
    }

    private class RingLabel extends JLabel {

        AbstractRing ring;
        TitledBorder ringBorder;
        JToolTip ringTip;

        public RingLabel(AbstractRing ring) {
            super();
            this.ring = ring;
            this.setSize(RING_WIDTH, RING_HEIGHT);
            this.setIcon(new TestRingIcon(RING_WIDTH / 2));
            this.setOpaque(true);
            this.setVisible(true);
            this.setEnabled(true);

            EtchedBorder eb = new EtchedBorder(EtchedBorder.RAISED);
            ringBorder = new TitledBorder(
                    eb,
                    this.ring.getAmountString(),
                    TitledBorder.RIGHT,
                    TitledBorder.BOTTOM
            );
            ringBorder.setTitleFont(new Font("SERIF", Font.BOLD, 10));
            ringBorder.setTitleColor(GameColors.buffNumber);
            this.setBorder(ringBorder);

            ringTip = new JToolTip();
            this.setToolTipText(GameToolTip.getHTML(
                    this.ring.name,
                    this.ring.getDescription()
            ));
        }

        @Override
        public JToolTip createToolTip() {
            return GameToolTip.getStyle(ringTip);
        }

        public void update() {
            this.setToolTipText(GameToolTip.getHTML(
                    this.ring.name,
                    this.ring.getDescription()
            ));
            ringBorder.setTitle(this.ring.getAmountString());
            if (ringBorder.getTitle().equals("")) { // 暂时性的替代方案：如果这个Ring不能叠层，显示N
                ringBorder.setTitle("N");
            }
            repaint();
        }

        public void destroy() {
            this.setVisible(false);
            this.setEnabled(false);
        }
    }
}
