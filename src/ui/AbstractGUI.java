//package ui;
//
//import cards.AbstractCard;
//import core.AbstractCreature;
//import core.AbstractPlayer;
//import dungeons.AbstractDungeon;
//import ui.window.CustomFrame;
//import ui.newwindow.CustomLabel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//
//public abstract class AbstractGUI {
//
//    static ArrayList<CustomLabel> labels; // 装目前场上显示的所有手牌的CustomLabel
//    private static JLabel healthBar;
//
//    private static JLabel energyPanel;
//    public CustomFrame frame;
//    public JButton endTurnButton;
//    private static JLabel playerPanel;
//
//    private class CreatureGUIset {
//
//        CustomFrame frame;
//        CreatureGUIset(CustomFrame frame, int healthBarX, int healthBarY) {
//            this.frame = frame;
//            // init health bar
//            healthBar = new JLabel();
//            healthBar.setFont(new Font("Inter",Font.PLAIN,24)); // 设置字体
//            healthBar.setBounds(315,439,216,33); // 设置大小
//            healthBar.setVerticalAlignment(JLabel.CENTER); // 排版
//            healthBar.setHorizontalAlignment(JLabel.LEFT);
//
//            // init block bar
//            blockBar = new JLabel();
//
//            // init creature image
//            image = new JLabel();
//
//            this.frame.add(healthBar);
//            this.frame.add(blockBar);
//        }
//
//        public void updateHealthBar(int hp, int hp_max) {
//            healthBar.setText(hp+"/"+hp_max);
//        }
//
//        public void updateBlockBar(int block) {
//            return;
//        }
//    }
//
////    private class PlayerGUIset extends CreatureGUIset {
////
////        PlayerGUIset(CustomFrame frame, int healthBarX, int healthBarY) {
////            super(frame, healthBarX, healthBarY);
////        }
////    }
//
//    private JLabel blockBar;
//    private JLabel image;
//    private JLabel buffPanel;
//    public static final int[] GuiSetLayoutX = {315, 800};
//    public static final int[] GuiSetLayoutY = {440, 440};
//    ArrayList<CreatureGUIset> CreatureGuiSets = new ArrayList<>();
//
//    // initialize player panel
//    public AbstractGUI(CustomFrame frame) {
//        this.frame = frame;
//        energyPanel = new JLabel();
//        endTurnButton = new JButton("End Turn");
//        int i = 0;
//        for (AbstractCreature c: AbstractDungeon.getCreatures()) {
//            if (i > 1) {
//                break;  // only 2 player so far
//            }
//            CreatureGuiSets.add(new CreatureGUIset(this.frame, GuiSetLayoutX[i], GuiSetLayoutY[i]));
//            i++;
//        }
//
//        // PLAYER'S NAME
//        playerPanel = new JLabel();
//        playerPanel.setFont(new Font("Inter",Font.PLAIN,24));
//        playerPanel.setBounds(85,340,95,49);
//        playerPanel.setVerticalAlignment(JLabel.CENTER); // 排版
//        playerPanel.setHorizontalAlignment(JLabel.LEFT);
//
//        this.frame.add(playerPanel);
//        this.frame.add(endTurnButton);
//        this.frame.add(energyPanel);
//    }
//
//    public static void updateBuffPanel() {}
//
//    public static void updateBlockBar(AbstractCreature c) {
//
//    }
//
//    public static void updateEnergyPanel(AbstractPlayer p) {
//        energyPanel.setText(p.energy+"/"+p.energyCap);
//    }
//
//    public static void updateHealthBar(AbstractCreature c) {
//        healthBar.setText(c.health+"/"+c.maxHP);
//    }
//
//    public static void updateOnStagePlayerName(AbstractPlayer p) {
//        playerPanel.setText(p.name);
//    }
//
//    public static void deathScreen() {
//    }
//
//    // private static ArrayList<CustomLabel> labels = new ArrayList<>();
//    public static void clearCardDisplay() {
//        for (CustomLabel l: labels) {
//            l.setVisible(false);
//            l.setEnabled(false);
//        }
//        labels.clear();
//    }
//
//    private static final int CARD_Y = 676;
//    private static final int CARD_X_START = 160;
//    private static final int CARD_X_END = 1200;
//    private static final int CARD_WIDTH = 150;
//    private static final int CARD_X_RANGE = CARD_X_END - CARD_X_START;
////    public static void reLayoutHand() {
////        clearCardDisplay();
////        int num = AbstractDungeon.onStagePlayer.hand.size();
////        int[] x_coors = new int[num];
////        for(int i=0; i<num; i++) {
////            x_coors[i] = (CARD_X_RANGE / (num + 1)) * i + (CARD_WIDTH / 2);
////        }
////        int j = 0;
////        for (AbstractCard card: AbstractDungeon.onStagePlayer.hand.deckCards) {
////            CustomLabel label = new CustomLabel(card, AbstractDungeon, x_coors[j], 1000);
////        }
////    }
//
//}
