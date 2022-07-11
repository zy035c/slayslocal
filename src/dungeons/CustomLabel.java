package dungeons;

import cards.AbstractCard;
import com.sun.management.VMOption;
import core.AbstractPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import core.AbstractCreature;
import core.AbstractPlayer;
import cards.AbstractCard;
import core.GUI;
import javax.swing.border.Border;

public class CustomLabel extends JLabel {
    private int originalX, originalY;
    public static int CARD_WIDTH = 180;
    public static int CARD_HEIGHT = 300;
    private AbstractCard card;
    private Exordium dungeon;
    GUI gui;
    CustomLabel myself;
    public CustomLabel(AbstractCard card, Exordium dungeon, int cardX, int cardY, GUI gui) {
        this.setFont(new Font("Inter", Font.PLAIN, 24));
        this.setOpaque(true);
        this.setBackground(new Color(0xd6d6d6));
        this.setText(card.NAME);

        // this.setBorder(b);

        originalX = cardX;
        originalY = cardY;
        changeCoor(originalX, originalY);
        addListener();

        this.card = card;
        this.dungeon = dungeon;
        this.gui = gui;
        // this.myself = this; // 我  传  我  自  己
    }

    // 166, 676
    // 376
    // 594
    // 832
    // 1066

    public void changeCoor(int x, int y){
        this.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
        originalX = x;
        originalY = y;
    }
    public void addListener() {

        MouseAdapter mAdapter = new MouseAdapter() {

//            private int mouse_original_x = 50;
//            private int mouse_original_y = 50;

            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(100,200,50));
            }

            public void mouseExited(MouseEvent e) {
                setBackground(new Color(0xd6d6d6));
            }

            public void mouseDragged(MouseEvent e) {
                setBounds(getX()+e.getX()-50,getY()+e.getY()-50, CARD_WIDTH, CARD_HEIGHT);
            }

            public void mouseReleased(MouseEvent e) {
                setBackground(new Color(0xd6d6d6));

                // 根据牌的作用类型（只能对自己，对一个敌人，对所有敌人...）来选择目标
                // 因为场上只有两个玩家所以直接选择敌人，除非牌的作用类型是SELF
                AbstractCreature target;
                switch(card.targetType) {
                    case SELF:
                        target = dungeon.onStagePlayer;
                        break;
                    case SINGLE:
                        target = dungeon.getEnemies().get(0);
                        break;
                    default:
                        target = dungeon.onStagePlayer;
                }

                if (466 < getX() && getX() < 919 &&  151 < getY() && getY() < 604) {
                    if (card.canUse(dungeon.onStagePlayer, target)) {
                        // 如果不能canUse那么也无法打出
                        // shoule be target selected
                        System.out.println("-> " + dungeon.onStagePlayer.name+ " played "
                                +card.NAME+". Target: "+target.name);

                        // remove label from screen
                        setVisible(false);
                        setEnabled(false);

                        // gui.labels.remove(myself); // remove this CustomLabel from GUI's labels
                        // 采用新办法：update时清空所有卡label，重新从hand中读取创建

                        dungeon.playCard(card, target);
                        gui.updateDungeonDisplay();
                        gui.updateCardDisplay();

                        return;
                    } else {
                        System.out.println("-> Cannot play "+card.NAME+".");
                        setBounds(originalX, originalY, CARD_WIDTH, CARD_HEIGHT);
                        return;
                    }
                } else {
                    setBounds(originalX, originalY, CARD_WIDTH, CARD_HEIGHT);
                }
            }

        };
        this.addMouseListener(mAdapter);
        this.addMouseMotionListener(mAdapter);
    }
}
