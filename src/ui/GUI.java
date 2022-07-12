package ui;

import cards.AbstractCard;
import core.AbstractCreature;
import dungeons.CustomFrame;
import dungeons.CustomLabel;
import dungeons.Exordium;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI {

    public boolean endTurn = false; // 没有什么用

    private Exordium dungeon; // 一个游戏场景的变量

    // static ArrayList<CustomLabel> labels; // 装目前场上显示的所有手牌的CustomLabel
    private JLabel healthBar;

    private JLabel energyPanel;
    public CustomFrame frame;
    public JButton endTurnButton;
    private static JLabel playerPanel;
    ArrayList<CreatureGUIset> GuiSets = new ArrayList<>();

    public JLabel draw_pile;
    public JLabel discard_pile;

    /******************************************************************************
     *
     * 在这里定义角色的加载位置
     * 比如说，有两个角色时摆放在屏幕的左和右
     * 有三个时把第三个摆放在上方，第四个在下方
     * 一种构想，还没有写完，目前只允许两个角色
     *
     ******************************************************************************/
    public final int[] GuiSetLayoutX = {80, 1100};
    public final int[] GuiSetLayoutY = {200, 200};
    public GUI(CustomFrame cf, Exordium dungeon) {
        // super(cf);
        this.frame = cf;
        this.frame.setLayout(null);

        this.dungeon = dungeon;

        // play area
        // 出牌区域
        JLabel playArea = new JLabel();
        playArea.setBounds(460,150,650,450);
        playArea.setVerticalAlignment(JLabel.CENTER); // 排版
        playArea.setHorizontalAlignment(JLabel.LEFT);
        playArea.setOpaque(false);
        playArea.setBackground(Color.CYAN);
        this.frame.add(playArea);

        // 结束回合 BUTTON
        endTurnButton = new JButton("End Turn");
        endTurnButton.setFont(new Font("Inter",Font.PLAIN,24));
        endTurnButton.setFocusable(false);
        endTurnButton.setBounds(880,516,166,63);
        endTurnButton.setBackground(Color.lightGray);
        // EndAction ea = new EndAction(this);
        // EndButton.addActionListener(ea);
        endTurnButton.addActionListener(e -> {
            System.out.println("-> " + dungeon.onStagePlayer.name+" Turn Button Clicked.");
            this.endTurn = true;
            this.dungeon.end_turn();
            this.dungeon.start_turn();
            this.updateDungeonDisplay();
            this.updateCardDisplay();
        }); // 按下之后触发
        this.frame.add(endTurnButton);


        // 初始化每个creature的UI套装
        int i = 0;
        for (AbstractCreature c: dungeon.getCreatures()) {
            if (i > 1) {
                break;  // only 2 player so far
            }
            this.GuiSets.add(new CreatureGUIset(this.frame, GuiSetLayoutX[i], GuiSetLayoutY[i], c));
            i++;
        }

        // PLAYER'S NAME
        // 显示当前玩家名字的区域
        playerPanel = new JLabel();
        playerPanel.setFont(new Font("Inter",Font.PLAIN,24));
        playerPanel.setBounds(60,10,200,49);
        playerPanel.setOpaque(true);
        playerPanel.setBackground(Color.GRAY);
        playerPanel.setVerticalAlignment(JLabel.CENTER); // 排版
        playerPanel.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(playerPanel);

        // 能量面板
        // energy panel
        this.energyPanel = new JLabel();
        energyPanel.setFont(new Font("Inter",Font.PLAIN,24));
        energyPanel.setOpaque(true);
        energyPanel.setBounds(430,500,80,80);
        energyPanel.setBackground(Color.YELLOW);
        energyPanel.setVerticalAlignment(JLabel.CENTER); // 排版
        energyPanel.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(energyPanel);

        // 抽牌堆弃牌堆
        this.draw_pile = new JLabel();
        this.discard_pile = new JLabel();
        draw_pile.setFont(new Font("Inter",Font.PLAIN,24));
        discard_pile.setFont(new Font("Inter",Font.PLAIN,24));
        draw_pile.setOpaque(true);
        discard_pile.setOpaque(true);
        draw_pile.setBounds(70,720,70,110);
        discard_pile.setBounds(CustomFrame.FRAME_WIDTH-140,720,70,110);
        draw_pile.setBackground(Color.YELLOW);
        discard_pile.setBackground(Color.YELLOW);
        draw_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        draw_pile.setHorizontalAlignment(JLabel.LEFT);
        discard_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        discard_pile.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(draw_pile);
        this.frame.add(discard_pile);
    }

    public void updatePlayerPanel() {
        playerPanel.setText(this.dungeon.onStagePlayer.name);
    }

    public void updateEnergyPanel() {
        System.out.println("Updating energy panel.");
        String e = Integer.toString(this.dungeon.onStagePlayer.energy) +
                "/" + Integer.toString(this.dungeon.onStagePlayer.energyCap);
        energyPanel.setText(e);
        System.out.println("Done. "+"Energy "+e+".");
    }

    public void updatePile() {
        System.out.println("Updating pile number.");
        String draw = Integer.toString(this.dungeon.onStagePlayer.drawPile.size());
        String discard = Integer.toString(this.dungeon.onStagePlayer.discardPile.size());
        this.draw_pile.setText(draw);
        this.discard_pile.setText(discard);
        System.out.println("Done. "+"Draw pile "+draw+" Discard pile "+discard+".");
    }

    /******************************************************************************
     * 这里是creature的UI套装
     * 因为有多少个creature就有多少套UI
     * 包括血条格挡条和图片...
     *
     ******************************************************************************/
    private class CreatureGUIset {

        CustomFrame frame;
        private JLabel blockBar;
        private JLabel image;
        private AbstractCreature c;
        private JLabel healthBar;

        private static final int IMAGE_HEIGHT = 300;
        private static final int HP_BAR_WIDTH = 200;
        CreatureGUIset(CustomFrame frame, int posX, int posY, AbstractCreature c) {
            this.c = c;
            this.frame = frame;

            // 生命条
            // init health bar
            healthBar = new JLabel();
            healthBar.setFont(new Font("Inter",Font.PLAIN,24)); // 设置字体
            healthBar.setBounds(posX, posY+IMAGE_HEIGHT+20, HP_BAR_WIDTH,30); // 设置大小
            healthBar.setOpaque(true);
            healthBar.setBackground(Color.RED);
            healthBar.setVerticalAlignment(JLabel.CENTER); // 排版
            healthBar.setHorizontalAlignment(JLabel.LEFT);

            // 格挡显示
            // init block bar
            blockBar = new JLabel();
            blockBar.setFont(new Font("Inter",Font.PLAIN,24)); // 设置字体
            blockBar.setBounds(posX+HP_BAR_WIDTH, posY+IMAGE_HEIGHT+18,32,34); // 设置大小
            blockBar.setOpaque(true);
            blockBar.setBackground(Color.MAGENTA);
            blockBar.setVerticalAlignment(JLabel.CENTER); // 排版
            blockBar.setHorizontalAlignment(JLabel.LEFT);

            // 角色图片
            // init creature image
            image = new JLabel("等待图片...");
            image.setOpaque(true);
            image.setBackground(Color.pink);
            image.setBounds(posX, posY, 200, IMAGE_HEIGHT);
            image.setVerticalAlignment(JLabel.CENTER); // 排版
            image.setHorizontalAlignment(JLabel.LEFT);

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

//    public void clearCardDisplay() {
//        for (CustomLabel l: labels) {
//            l.setVisible(false);
//            l.setEnabled(false);
//        }
//        labels.clear();
//    }

    public void updateDungeonDisplay() {
        updatePlayerPanel();
        updateEnergyPanel();
        updatePile();
        for(CreatureGUIset GuiSet: GuiSets) {
            GuiSet.updateHealthBar();
            GuiSet.updateBlockBar();
        }
    }

    public ArrayList <CustomLabel> labels = new ArrayList<>(); // 装目前场上显示的所有手牌的CustomLabel

    private static final int CARD_Y = 670;
    private static final int CARD_X_START = 160;
    private static final int CARD_X_END = 1200;
    private static final int CARD_WIDTH = 180;
    private static final int CARD_X_RANGE = CARD_X_END - CARD_X_START;

    public void clearCardDisplay() {
        for (CustomLabel label: this.labels) {
            label.setVisible(false);
            label.setEnabled(false);
        }
        this.labels.clear();

    }


    public void updateCardDisplay() {
        clearCardDisplay();
        int num = dungeon.onStagePlayer.hand.size(); // 获取当前场上player的手牌数量
        System.out.println("Updating screen card labels: "+num);
        int[] x_corr = new int[num];
        for (int i=0; i<num; i++) {
            x_corr[i] = CARD_X_START + (CARD_X_RANGE / (num + 1)) * (i+1) - CARD_WIDTH/2;
        }

        int i = 0;
        for (AbstractCard card: dungeon.onStagePlayer.hand.deckCards) {
            CustomLabel label = new CustomLabel(card, dungeon, x_corr[i], CARD_Y, this);
            this.labels.add(label);
            this.frame.add(label);
            System.out.println("Generated one card label.");
            System.out.println("Generated at x "+x_corr[i]);
            i++;
        }
        System.out.println("Updating screen card labels done.");
    }


//    public static void main(String[] args) {
//        CustomFrame frame = new CustomFrame();
//
//        Player p1 = new Player("p1");
//        Player p2 = new Player("p2");
//        ArrayList<AbstractCreature> p_list = new ArrayList<>();
//        p_list.add(p1);
//        p_list.add(p2);
//
//        Exordium dungeon = new Exordium(p_list);
//        dungeon.init_dungeon();
//
//        GUI gui = new GUI(frame, dungeon);
//        gui.updateDungeonDisplay(dungeon);
//
//        while(true) {
//            while(gui.endTurn) {
//                gui.endTurn = false;
//                dungeon.start_turn();
//                gui.displayHand(dungeon);
//            }
//        }
//        while(gui.endTurn) {
//
//            gui.endTurn = false;
//            dungeon.start_turn();
//            gui.displayHand(dungeon);
//
//            while(true) {
//                if (dungeon.updated) {
//                    gui.updateCardDisplay(dungeon);
//                    gui.updateDungeonDisplay(dungeon);
//                }
//
//            }
//
//
//
//
//        }
//    }

}

/*
    GUI元素:
        1 矩形 代表生命条 (HP: 100/100)
        1 圆形 代表能量 (3/3)
        2 矩形 代表抽牌弃牌堆 (Deal Pile)  (Discard Pile)
        5 矩形 代表手牌 (Hand Card 1-5)
        3或4 button (Play Selected Card) (End turn) (Deck) (Option)
 */
