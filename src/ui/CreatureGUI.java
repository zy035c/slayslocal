package ui;

import buffs.AbstractBuff;
import buffs.CompromiseBuff;
import buffs.NoDrawBuff;
import buffs.StrengthBuff;
import core.AbstractCreature;
import core.AbstractPlayer;
import core.Player;
import rings.Jormungandr;
import ui.tooltips.GameToolTip;
import ui.window.CustomFrame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import static ui.GUI.calcX;
import static ui.GUI.calcY;

/******************************************************************************
 * 这里是creature的UI套装
 * 因为有多少个creature就有多少套UI
 * 包括血条格挡条和图片...
 ******************************************************************************/
public class CreatureGUI {

    CustomFrame frame;
    private JLabel blockBar;
    private JLabel image;
    private AbstractCreature c;
    private JLabel healthBar;
    private BuffGUI buffGui;
    private RingBar ringBar;

    private final int IMAGE_WIDTH = calcX(200);
    private final int IMAGE_HEIGHT = calcY(300);
    private final int HP_BAR_WIDTH = calcX(200);
    private final int HP_BAR_HEIGHT = calcY(16);
    private final int BLOCK_BAR_HEIGHT = calcX(26);
    private final int BLOCK_BAR_WIDTH = BLOCK_BAR_HEIGHT;
    private int uiX;
    private int uiY;


    public CreatureGUI(CustomFrame frame, int posX, int posY, AbstractCreature c) {
        this.c = c;
        this.frame = frame;
        this.uiX = posX;
        this.uiY = posY;

        // 生命条
        // init health bar
        healthBar = new JLabel();
        healthBar.setFont(new Font("Monospaced",Font.PLAIN,18)); // 设置字体
        healthBar.setBorder(new BevelBorder(BevelBorder.RAISED));
        healthBar.setBounds(
                uiX,
                posY + IMAGE_HEIGHT + calcY(10),
                HP_BAR_WIDTH,
                HP_BAR_HEIGHT
        ); // 设置大小
        healthBar.setOpaque(true);
        healthBar.setBackground(GameColors.healthBar);
        healthBar.setForeground(Color.white);
        healthBar.setVerticalAlignment(JLabel.CENTER); // 排版
        healthBar.setHorizontalAlignment(JLabel.CENTER);

        // 格挡显示
        // init block bar
        blockBar = new JLabel() {
            @Override
            public JToolTip createToolTip() {
                return GameToolTip.getStyle(new JToolTip());
            }
        };
        blockBar.setBounds(
                uiX + HP_BAR_WIDTH,
                uiY + IMAGE_HEIGHT + calcY(4),
                BLOCK_BAR_WIDTH,
                BLOCK_BAR_HEIGHT
        ); // 设置大小
        blockBar.setOpaque(true);
        blockBar.setForeground(Color.white);
        Font shieldFont = new Font("SAN_SERIF",Font.PLAIN, 14);
        blockBar.setFont(shieldFont);
        blockBar.setBackground(GameColors.shieldBlue);
        blockBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        blockBar.setVerticalAlignment(JLabel.CENTER); // 排版
        blockBar.setHorizontalAlignment(JLabel.CENTER);
        blockBar.setToolTipText(GameToolTip.getBlockTipText());

        // 角色图片
        // init creature image
        image = new JLabel("等待图片...");
        image.setOpaque(true);
        image.setBackground(GameColors.cguiPlaceholder);
        image.setBounds(
                uiX,
                uiY,
                IMAGE_WIDTH,
                IMAGE_HEIGHT
        );
        image.setVerticalAlignment(JLabel.CENTER); // 排版
        image.setHorizontalAlignment(JLabel.LEFT);

        // buff条
        int buffX = uiX;
        int buffY = uiY + IMAGE_HEIGHT + calcY(26) + HP_BAR_HEIGHT;
        buffGui = new BuffGUI(this.c, buffX, buffY);
        if (c.buffs.size() > 0) {
            updateBuffBar();
        }

        // 如果是玩家：ring条
        if (this.c.isPlayer) {
            AbstractPlayer p = (AbstractPlayer) this.c;
            int ringX = uiX;
            int ringY = uiY + IMAGE_HEIGHT + calcY(30) + HP_BAR_HEIGHT + BuffGUI.BUFF_HEIGHT;
            ringBar = new RingBar((AbstractPlayer) p, ringX, ringY);
            if (p.rings.size() > 0) {
                updateRingBar();
            }
            this.frame.addComp(ringBar, JLayeredPane.PALETTE_LAYER);
        } //


        this.frame.addComp(healthBar, JLayeredPane.PALETTE_LAYER);
        this.frame.addComp(blockBar, JLayeredPane.PALETTE_LAYER);
        this.frame.addComp(image, JLayeredPane.PALETTE_LAYER);
        this.frame.addComp(buffGui, JLayeredPane.PALETTE_LAYER);
    }

    public void updateHealthBar() {
        System.out.println("Updating "+c.name+"'s HP bar.");
        String hp = c.health+"/"+c.maxHP;
        healthBar.setText(hp);
        System.out.println("Done. "+c.name+" HP "+hp+".");
    }

    public void updateBlockBar() {
        System.out.println("Updating "+c.name+" block labels.");
        String b = Integer.toString(c.block);
        this.blockBar.setText(b);
        System.out.println("Done. "+c.name+" block "+b+".");
    }

    public boolean cardOnCreature(CardPane pane) {
        return new Rectangle(
                this.image.getX(),
                this.image.getY(),
                this.IMAGE_WIDTH,
                this.IMAGE_HEIGHT
        ).intersects(pane.getRect());
    }

    public AbstractCreature getCreature() {
        return this.c;
    }

    public void updateBuffBar() { this.buffGui.update(); }

    public void updateRingBar() {
        if (c.isPlayer) {
            this.ringBar.inBattleUpdate();
        }
    }


    @Deprecated
    public void initBuffBar() {
        final int BUFF_WIDTH = calcX(30);
        final int BUFF_HEIGHT = BUFF_WIDTH;
        final int BUFF_SEPERATION = calcX(14);

        if (c.buffs.size() <= 0) {
            return;
        }
        int buffX = uiX;
        int buffY = uiY + IMAGE_HEIGHT + calcY(18) + HP_BAR_HEIGHT + calcY(25);
        int i = 0;
        for (AbstractBuff buff: c.buffs) {
            JLabel buffLabel = new JLabel();
            buffLabel.setOpaque(true);
            buffLabel.setBackground(GameColors.purple1);
            buffLabel.setForeground(Color.WHITE);
            buffLabel.setBounds(
                    buffX + BUFF_SEPERATION * i,
                    buffY,
                    BUFF_WIDTH,
                    BUFF_HEIGHT
            );
            buffLabel.setText(Integer.toString(buff.stack));
            System.out.println(buff.getDescription());
           //  buffLabel.setToolTipText(buff.getDescription());
            buffLabel.setVisible(true);
            this.frame.add(buffLabel);
            i++;
        }
    }

    // @Test
    public static void main(String[] args) {
        AbstractPlayer pmonster = new Player("BuffTestPlayer");
        pmonster.buffs.add(new CompromiseBuff(pmonster, 2));
        pmonster.buffs.add(new StrengthBuff(pmonster, 4, false));
        pmonster.buffs.add(new NoDrawBuff(pmonster, 1, false));
        pmonster.obtainRing(new Jormungandr(pmonster));
        CustomFrame frame = new CustomFrame();
        CreatureGUI cgui = new CreatureGUI(frame, 400, 180, pmonster);
        cgui.updateHealthBar();
        cgui.updateBlockBar();
        frame.repaint();
    }
}

/******************************************************************************
 *
 *    *******    *       *    ******
 *    **         * *     *    *     **
 *    *******    *   *   *    *      *
 *    **         *     * *    *     **
 *    *******    *       *    ******
 *
 ******************************************************************************/
