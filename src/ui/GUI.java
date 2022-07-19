package ui;

import cards.Deck;
import ui.listeners.PileMouseListener;
import ui.window.CustomFrame;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public boolean endTurn = false; // 没有什么用

    private JLabel energyPanel;
    public CustomFrame frame;
    public JButton endTurnButton;
    private static JLabel playerPanel;

    public JLabel draw_pile;
    public JLabel discard_pile;
    public JLabel exhaust_pile;

    // private int F_WIDTH = CustomFrame.FRAME_WIDTH;
    // private int F_HEIGHT = CustomFrame.FRAME_HEIGHT;

    public static int calcX(int x) {
        return (int) ((x / 1440.0) * CustomFrame.FRAME_WIDTH);
    }
    public static int calcY(int y) {
        return (int) ((y / 1024.0) * CustomFrame.FRAME_HEIGHT);
    }

    public GUI(CustomFrame cf) {
        // super(cf);
        this.frame = cf;
        this.frame.setLayout(null);

        // 结束回合 BUTTON
        endTurnButton = new JButton("End Turn");
        endTurnButton.setFont(new Font("Inter", Font.PLAIN, 24));
        endTurnButton.setFocusable(false);
        endTurnButton.setBounds(calcX(880), calcY(516), calcY(166), calcY(63));
        endTurnButton.setBackground(Color.lightGray);
        // EndAction ea = new EndAction(this);
        // EndButton.addActionListener(ea);

        this.frame.add(endTurnButton);

        // PLAYER'S NAME
        // 显示当前玩家名字的区域
        playerPanel = new JLabel();
        playerPanel.setFont(new Font("Inter", Font.PLAIN, 20));
        playerPanel.setBounds(calcX(60), calcY(10), calcX(200), calcY(49));
        playerPanel.setOpaque(true);
        playerPanel.setBackground(Color.GRAY);
        playerPanel.setVerticalAlignment(JLabel.CENTER); // 排版
        playerPanel.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(playerPanel);


        // 能量面板
        // energy panel
        this.energyPanel = new JLabel();
        energyPanel.setFont(new Font("Inter", Font.PLAIN, 18));
        energyPanel.setOpaque(true);
        energyPanel.setBounds(calcX(430), calcY(500), calcX(80), calcY(80));
        energyPanel.setBackground(Color.YELLOW);
        energyPanel.setVerticalAlignment(JLabel.CENTER); // 排版
        energyPanel.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(energyPanel);


        // 抽牌堆弃牌堆
        this.draw_pile = new JLabel();
        this.discard_pile = new JLabel();
        draw_pile.setFont(new Font("Inter", Font.PLAIN, 18));
        discard_pile.setFont(new Font("Inter", Font.PLAIN, 18));
        draw_pile.setOpaque(true);
        discard_pile.setOpaque(true);
        draw_pile.setBounds(calcX(70), calcY(770), calcX(70), calcY(110));
        discard_pile.setBounds(calcX(1300), calcY(770), calcX(70), calcY(110));
        draw_pile.setBackground(Color.YELLOW);
        discard_pile.setBackground(Color.YELLOW);
        draw_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        draw_pile.setHorizontalAlignment(JLabel.LEFT);
        discard_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        discard_pile.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(draw_pile);
        this.frame.add(discard_pile);


        // 烧牌堆
        exhaust_pile = new JLabel();
        exhaust_pile.setFont(new Font("Inter", Font.PLAIN, 18));
        exhaust_pile.setOpaque(true);
        exhaust_pile.setBounds(calcX(1250), calcY(640), calcX(40), calcY(40));
        exhaust_pile.setBackground(GameColors.dark1);
        exhaust_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        exhaust_pile.setHorizontalAlignment(JLabel.LEFT);
        this.frame.add(exhaust_pile);
    }

    // 其实也可以在外部设置(DungeonScene)
    public void setPiles(Deck draw, Deck discard, Deck exhaust, Deck masterdeck) {
        PileMouseListener draw_listener = new PileMouseListener(draw);
        draw_pile.addMouseListener(draw_listener);
        PileMouseListener discard_listener = new PileMouseListener(discard);
        discard_pile.addMouseListener(discard_listener);
        PileMouseListener exhaust_listener = new PileMouseListener(exhaust);
        exhaust_pile.addMouseListener(exhaust_listener);
        // 缺了一个masterdeck
    }

    public static Rectangle getPlayArea() {
        // play area
        // 出牌区域
        // in 1440 x 1024
        return new Rectangle(
                calcX(550), // 550 + 170 = 720 = (1/2)1440
                calcY(180),
                calcX(340),
                calcY(200)
        );
    }

    public void updatePlayerPanel(String currentPlayerName) {
        playerPanel.setText(currentPlayerName);
    }

    public void updateEnergyPanel(int energy, int energyCap) {
        System.out.println("Updating energy panel.");
        String e = Integer.toString(energy) +
                "/" + Integer.toString(energyCap);
        energyPanel.setText(e);
        System.out.println("Done. " + "Energy " + e + ".");
    }

    public void updateDiscardNumber(int num) {
        String discard = Integer.toString(num);
        this.discard_pile.setText(discard);
        System.out.println("GUI updating discard pile num. Discard pile " + discard + ".");
    }

    public void updateDrawNumber(int num) {
        String draw = Integer.toString(num);
        this.draw_pile.setText(draw);
        System.out.println("GUI updating draw pile num. Draw pile " + draw + ".");
    }

    // 没有烧牌堆更新。在外部更新吧

}



/*
    GUI元素:
        1 矩形 代表生命条 (HP: 100/100)
        1 圆形 代表能量 (3/3)
        2 矩形 代表抽牌弃牌堆 (Deal Pile)  (Discard Pile)
        5 矩形 代表手牌 (Hand Card 1-5)
        3或4 button (Play Selected Card) (End turn) (Deck) (Option)
 */
