package ui;

import buff.AbstractBuff;
import core.AbstractCreature;
import ui.window.CustomFrame;

import javax.swing.*;
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

    private final int IMAGE_WIDTH = calcX(200);
    private final int IMAGE_HEIGHT = calcY(300);
    private final int HP_BAR_WIDTH = calcX(200);
    private final int HP_BAR_HEIGHT = calcY(30);
    private final int BLOCK_BAR_HEIGHT = calcX(34);
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
        healthBar.setFont(new Font("Inter",Font.PLAIN,18)); // 设置字体
        healthBar.setBounds(
                uiX,
                posY + IMAGE_HEIGHT + calcY(20),
                HP_BAR_WIDTH,
                HP_BAR_HEIGHT
        ); // 设置大小
        healthBar.setOpaque(true);
        healthBar.setBackground(Color.RED);
        healthBar.setVerticalAlignment(JLabel.CENTER); // 排版
        healthBar.setHorizontalAlignment(JLabel.LEFT);

        // 格挡显示
        // init block bar
        blockBar = new JLabel();
        blockBar.setFont(new Font("Inter",Font.PLAIN,18)); // 设置字体
        blockBar.setBounds(
                uiX + HP_BAR_WIDTH,
                uiY + IMAGE_HEIGHT + calcY(18),
                BLOCK_BAR_WIDTH,
                BLOCK_BAR_HEIGHT
        ); // 设置大小
        blockBar.setOpaque(true);
        blockBar.setBackground(Color.MAGENTA);
        blockBar.setVerticalAlignment(JLabel.CENTER); // 排版
        blockBar.setHorizontalAlignment(JLabel.LEFT);

        // 角色图片
        // init creature image
        image = new JLabel("等待图片...");
        image.setOpaque(true);
        image.setBackground(Color.pink);
        image.setBounds(
                uiX,
                uiY,
                IMAGE_WIDTH,
                IMAGE_HEIGHT
        );
        image.setVerticalAlignment(JLabel.CENTER); // 排版
        image.setHorizontalAlignment(JLabel.LEFT);

        // buff条
        if (c.buffs.size() > 0) {
            updateBuffBar();
        }

        this.frame.add(healthBar);
        this.frame.add(blockBar);
        this.frame.add(image);
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

    private final int BUFF_WIDTH = calcX(32);
    private final int BUFF_HEIGHT = BUFF_WIDTH;
    private final int BUFF_SEPERATION = calcX(14);

    public void updateBuffBar() {
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
            buffLabel.setBounds(
                    buffX + BUFF_SEPERATION * i,
                    buffY,
                    BUFF_WIDTH,
                    BUFF_HEIGHT
            );
            buffLabel.setText(buff.ID);
            this.frame.add(buffLabel);
            i++;
        }
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
