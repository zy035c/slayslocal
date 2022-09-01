package ui;

import buffs.AbstractBuff;
import buffs.CompromiseBuff;
import buffs.NoDrawBuff;
import buffs.StrengthBuff;
import core.AbstractCreature;
import core.AbstractPlayer;
import core.Player;
import ui.icons.TestBuffIcon;
import ui.tooltips.GameToolTip;
import ui.window.CustomFrame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import static ui.GUI.calcX;

public class BuffGUI extends JLayeredPane {

    public static final int BUFF_WIDTH = calcX(50);
    public static final int BUFF_HEIGHT = BUFF_WIDTH;
    public static final int BUFF_SEPARATION = calcX(5);

    private class BuffLabel extends JLabel {

        AbstractBuff buff;
        JToolTip buffTip;
        TitledBorder buffBorder;

        BuffLabel(AbstractBuff buff) {
            super();
            this.buff = buff;
            this.setSize(BUFF_WIDTH, BUFF_HEIGHT);
            this.setIcon(new TestBuffIcon(BUFF_WIDTH / 2));
            this.setOpaque(true);

            EtchedBorder eb = new EtchedBorder(EtchedBorder.RAISED);
            buffBorder = new TitledBorder(
                    eb,
                    this.buff.getStackString(),
                    TitledBorder.RIGHT,
                    TitledBorder.BOTTOM
            );
            buffBorder.setTitleFont(new Font("SERIF", Font.BOLD, 10));
            buffBorder.setTitleColor(GameColors.buffNumber);
            this.setBorder(buffBorder);

            buffTip = new JToolTip();
            this.setToolTipText(GameToolTip.getHTML(
                    this.buff.ID,
                    this.buff.getDescription()
            ));
        }

        @Override
        public JToolTip createToolTip() {
            return GameToolTip.getStyle(buffTip);
        }

        private void update() {
            this.setToolTipText(GameToolTip.getHTML(
                    this.buff.NAME,
                    this.buff.getDescription()
            ));
            buffBorder.setTitle(this.buff.getStackString());
            if (buffBorder.getTitle().equals("")) { // 暂时性的替代方案：如果这个buff不能叠层，显示N
                buffBorder.setTitle("N");
            }
            repaint();
        }

        public void destroy() {
            setEnabled(false);
            setVisible(false);
        }
    }

    ArrayList<BuffLabel> buffLabels;
    AbstractCreature creature;

    public BuffGUI(AbstractCreature creature, int x, int y) {
        super();
        buffLabels = new ArrayList<>();
        this.creature = creature;
        BoxLayout buffBarLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(buffBarLayout);
        this.setEnabled(true);
        this.setBounds(
                x,
                y,
                500,
                BUFF_HEIGHT + calcX(2)
        );
    }

    public void update() {
        //
        System.out.println("Updating " + this.creature.name
                + " buff. Buffs size = " + this.creature.buffs.size());
        if (buffLabels.isEmpty() && creature.buffs.isEmpty()) return;
        if (creature.buffs.isEmpty()) { clear(); return; }

        ArrayList<AbstractBuff> buffToAdd = new ArrayList<>(creature.buffs);
        for (int i = 0; i < buffLabels.size(); i++) {
            for (int j = 0; j < creature.buffs.size(); j++) {
                if (buffLabels.get(i).buff.ID.equals(creature.buffs.get(j).ID)) {
                    buffLabels.get(i).update(); // 如果前台显示的buff（buffLabels）里已经有这个buff，那么更新数量。
                    buffToAdd.remove(creature.buffs.get(j)); // 并且从buffToAdd中移除这个buff
                    break;
                } else if (j == creature.buffs.size() - 1) {
                    // 最后：现在的buffLabels里有，但是没有在creature.buffs里，删除
                    buffLabels.get(i).destroy();
                }
            }
        }
        if (buffToAdd.size() > 0) {
            for (AbstractBuff buff: buffToAdd) {
                BuffLabel label = new BuffLabel(buff);
                this.add(label);
                this.add(Box.createRigidArea(new Dimension(BUFF_SEPARATION, 10)));
                // System.out.println("---New Buff Label added -" + label.buff.ID +
                // " " + label.buff.amount);
                buffLabels.add(label);
                label.update();
            }
        }
        assert buffLabels.size() == this.creature.buffs.size();
        // repaint();
    }

    private void clear() {
        for (BuffLabel label: this.buffLabels) {
            label.setEnabled(false);
            label.setVisible(false);
        }
        buffLabels.clear();
        repaint();
        revalidate();
    }

    // @Test 测试用
    public static void main(String[] args) {
        CustomFrame frame = new CustomFrame();
        JLabel label = new JLabel();
        label.setBounds(100, 100, 300, 100);
        label.setBackground(Color.BLACK);
        label.setVisible(true);
        label.setEnabled(true);
        label.setOpaque(true);
        label.setForeground(Color.white);
        label.setText("class BuffGUI test");

        AbstractCreature monster = new Player("BuffTestPlayer");
        monster.buffs.add(new CompromiseBuff(monster, 2));
        monster.buffs.add(new StrengthBuff(monster, 4, false));
        monster.buffs.add(new NoDrawBuff((AbstractPlayer)monster, 1, false));

        BuffGUI bgui = new BuffGUI(monster, 100, 100+100+20);
        frame.add(label);
        frame.add(bgui);
        bgui.update();
        frame.repaint();
        frame.revalidate();
        // Compromise: 2
        // Strength: 4
        // No Draw
    }
}
