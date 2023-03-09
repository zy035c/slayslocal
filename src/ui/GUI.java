package ui;

import cards.Deck;
import ui.icons.EnergyIcon;
import ui.listeners.PileMouseListener;
import ui.tooltips.GameToolTip;
import ui.window.CustomFrame;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/******************************************************************************
 * 这里包含的元素：能量面板、结束回合按钮、抽牌堆、弃牌堆、烧牌堆
 * 上菜单栏 @ui.TopMenuBar
 * ！注意 玩家和怪物的UI面板不在这里。
 * 在线程@core.DungeonScene里初始化 @ui.CreatureGUI
 ******************************************************************************/
public class GUI {

    private JLabel energyPanel;
    public CustomFrame frame;
    public JButton endTurnButton;
    public TopMenuBar topMenuBar;

    public JLabel draw_pile;
    public JLabel discard_pile;
    public JLabel exhaust_pile;

    public static int calcX(int x) {
        return (int) ((x / 1440.0) * CustomFrame.FRAME_WIDTH);
    }
    public static int calcY(int y) {
        return (int) ((y / 1024.0) * CustomFrame.FRAME_HEIGHT);
    }

    private static final int E_SIDE_LENGTH = calcX(100);

    private TitledBorder drawBorder;
    private TitledBorder discardBorder;

    public GUI(CustomFrame cf) {
        // super(cf);
        this.frame = cf;

        // 顶部菜单栏
        topMenuBar = new TopMenuBar();
        this.frame.addComp(topMenuBar, JLayeredPane.PALETTE_LAYER);

        // 结束回合 BUTTON
        endTurnButton = new JButton("End Turn"){
            @Override
            public JToolTip createToolTip() {
                return GameToolTip.getStyle(new JToolTip());
            }
        };
        endTurnButton.setFont(new Font("Inter", Font.PLAIN, 24));
        endTurnButton.setFocusable(false);
        endTurnButton.setBounds(
                calcX(880),
                calcY(516),
                calcY(166),
                calcY(63)
        );
        endTurnButton.setBackground(Color.lightGray);
        endTurnButton.setToolTipText(GameToolTip.getEndTurnTipText());
        this.frame.addComp(endTurnButton, JLayeredPane.MODAL_LAYER);

        // 能量面板
        // energy panel
        JLayeredPane energyPane = new JLayeredPane();
        energyPane.setBounds(
                calcX(430),
                calcY(500),
                E_SIDE_LENGTH,
                E_SIDE_LENGTH
        );
        energyPane.setOpaque(false);

        // field: JLabel energyPanel
        energyPanel = new JLabel() {
            @Override
            public JToolTip createToolTip() {
                return GameToolTip.getStyle(new JToolTip());
            }
        };
        energyPanel.setToolTipText(GameToolTip.getEnergyPanelText());
        energyPanel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        energyPanel.setForeground(Color.WHITE);
        energyPanel.setText("0/0");
        energyPanel.setVerticalAlignment(JLabel.CENTER); // 排版
        energyPanel.setHorizontalAlignment(JLabel.CENTER);
        int borderToHide = E_SIDE_LENGTH / 8;
        energyPanel.setBounds(
                borderToHide,
                borderToHide * 3,
                E_SIDE_LENGTH - 2 * borderToHide,
                E_SIDE_LENGTH - 6 * borderToHide
        );

        JLabel eIconLabel = new JLabel();
        eIconLabel.setBounds(
                0,
                0,
                E_SIDE_LENGTH,
                E_SIDE_LENGTH
        );
        eIconLabel.setIcon(new EnergyIcon((E_SIDE_LENGTH))); // 设置图标：@ui.icons.EnergyIcon
        eIconLabel.setOpaque(true);
        eIconLabel.setVisible(true);

        energyPane.add(energyPanel, 0);
        energyPane.add(eIconLabel, 1);

        energyPane.revalidate();
        energyPane.repaint();
        this.frame.addComp(energyPane, JLayeredPane.MODAL_LAYER);

        // 抽牌堆
        this.draw_pile = new JLabel();
        draw_pile.setOpaque(true);
        draw_pile.setBounds(
                calcX(70),
                calcY(770),
                calcX(65),
                calcY(100)
        );
        draw_pile.setBackground(GameColors.pile1);
        draw_pile.setVerticalAlignment(JLabel.CENTER);
        draw_pile.setHorizontalAlignment(JLabel.CENTER);

        // 弃牌堆
        this.discard_pile = new JLabel();
        discard_pile.setOpaque(true);
        discard_pile.setBounds(
                calcX(1300),
                calcY(770),
                calcX(65),
                calcY(100)
        );
        discard_pile.setBackground(GameColors.pile2);
        discard_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        discard_pile.setHorizontalAlignment(JLabel.CENTER);

        // 摸牌堆数字显示
        EtchedBorder drawEB = new EtchedBorder(
                EtchedBorder.LOWERED,
                GameColors.pile1,
                GameColors.pile1shadow
        ); // 最外层border
        LineBorder lineBorderDraw = new LineBorder(
                GameColors.pile1border,
                2,
                true);
        drawBorder = new TitledBorder(lineBorderDraw, "0");

        drawBorder.setTitlePosition(TitledBorder.BOTTOM);
        drawBorder.setTitleColor(Color.WHITE);
        drawBorder.setTitleFont(new Font("Monospaced", Font.PLAIN, 16));
        CompoundBorder drawEx = new CompoundBorder(drawEB, drawBorder);
        draw_pile.setBorder(drawEx);
        this.frame.addComp(draw_pile, JLayeredPane.MODAL_LAYER);

        // 弃牌堆数字显示
        EtchedBorder discardEB = new EtchedBorder(
                EtchedBorder.LOWERED,
                GameColors.pile2,
                GameColors.pile2shadow
        ); // 最外层border
        LineBorder lineBorderDisc = new LineBorder(
                GameColors.pile2border,
                2,
                true);
        discardBorder = new TitledBorder(lineBorderDisc, "0");
        discardBorder.setTitlePosition(TitledBorder.BOTTOM);
        discardBorder.setTitleColor(Color.WHITE);
        discardBorder.setTitleFont(new Font("Monospaced", Font.PLAIN, 16));
        CompoundBorder discardEx = new CompoundBorder(discardEB, discardBorder);
        discard_pile.setBorder(discardEx);
        this.frame.addComp(discard_pile, JLayeredPane.MODAL_LAYER);

        // 烧牌堆
        exhaust_pile = new JLabel();
        exhaust_pile.setFont(new Font("Inter", Font.PLAIN, 18));
        exhaust_pile.setOpaque(true);
        exhaust_pile.setBounds(calcX(1250), calcY(640), calcX(40), calcY(40));
        exhaust_pile.setBackground(GameColors.dark1);
        exhaust_pile.setVerticalAlignment(JLabel.CENTER); // 排版
        exhaust_pile.setHorizontalAlignment(JLabel.CENTER);
        this.frame.addComp(exhaust_pile, JLayeredPane.MODAL_LAYER);

        // 主要显示
        mainCaption = new MainCaption();
        mainCaption.setBounds(MAIN_X, MAIN_Y, MAIN_WIDTH, MAIN_HEIGHT);
        this.frame.addComp(mainCaption, JLayeredPane.POPUP_LAYER);
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

    private static final int MAIN_X = calcX(550); // 550 + 170 = 720 = (1/2)1440
    private static final int MAIN_Y = calcY(180);
    private static final int MAIN_WIDTH = calcX(340);
    private static final int MAIN_HEIGHT = calcY(200);

    public static Rectangle getPlayArea() {
        // play area
        // 出牌区域
        // in 1440 x 1024
        return new Rectangle(MAIN_X, MAIN_Y, MAIN_WIDTH, MAIN_HEIGHT);
    }

    public MainCaption mainCaption;
    public static class MainCaption extends JLabel {
        public JLabel mainLabel;
        public JLabel minorLabel;
        public MainCaption() {
            super();
            setEnabled(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            mainLabel = new JLabel();
            mainLabel.setForeground(GameColors.mainText);
            mainLabel.setHorizontalAlignment(CENTER);
            mainLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 32));
            Dimension mainSize = new Dimension(MAIN_WIDTH, MAIN_HEIGHT / 3);
            mainLabel.setMinimumSize(mainSize);
            mainLabel.setPreferredSize(mainSize);
            mainLabel.setMaximumSize(mainSize);
            // mainLabel.setBackground(Color.GRAY);
            mainLabel.setOpaque(true);
            mainLabel.setVisible(true);
            mainLabel.setEnabled(true);
            add(Box.createVerticalGlue());
            add(mainLabel);

            minorLabel = new JLabel();
            minorLabel.setForeground(GameColors.mainText);
            minorLabel.setHorizontalAlignment(CENTER);
            minorLabel.setFont(new Font("SERIF", Font.PLAIN, 16));
            Dimension minorSize = new Dimension(MAIN_WIDTH, MAIN_HEIGHT / 5);
            minorLabel.setMinimumSize(minorSize);
            minorLabel.setPreferredSize(minorSize);
            minorLabel.setMaximumSize(minorSize);
            // minorLabel.setBackground(Color.YELLOW);
            minorLabel.setOpaque(true);
            minorLabel.setVisible(true);
            minorLabel.setEnabled(true);
            add(minorLabel);

            repaint();
            revalidate();
        }
        public void setMain(String text) {
            mainLabel.setText(text);
        }
        public void setMinor(String text) {
            minorLabel.setText(text);
        }
    }

    public void setMainCaption(String text) {
        this.mainCaption.setMain(text);
    }

    public void setMainCaption(String text, int num) {
        this.mainCaption.setMain(text);

        String str; // 1st, 2nd, 3rd, 11th, 21st...
        if (num % 10 == 1 && num != 11) {
            str = "st Turn";
        } else if (num % 10 == 2 && num != 12) {
            str = "nd Turn";
        } else {
            str = "th Turn";
        }
        str = Integer.toString(num) + str;
        this.mainCaption.setMinor(str);
    }

    public void clearMainCaption() {
        this.mainCaption.setMain("");
        this.mainCaption.setMinor("");
    }

    public void updatePlayerPanel(String currentPlayerName) {
        topMenuBar.setName(currentPlayerName);
    }

    public void updateEnergyPanel(int energy, int energyCap) {
        System.out.println("Updating energy panel.");
        String e = Integer.toString(energy) +
                "/" + Integer.toString(energyCap);
        energyPanel.setText(e);
        energyPanel.repaint();
        System.out.println("Done. " + "Energy " + e + ".");
    }

    public void updateDiscardNumber(int numDis) {
        String discard = Integer.toString(numDis);
        discardBorder.setTitle(discard);
        discard_pile.repaint();
        System.out.println("GUI updating discard pile num " + discard + ".");
    }

    public void updateDrawNumber(int numDraw) {
        String draw = Integer.toString(numDraw);
        drawBorder.setTitle(draw);
        draw_pile.repaint();
        System.out.println("GUI updating draw pile num. " + draw + ".");
    }

    public void updateExhaustNumber(int num) {
        String exhaust = Integer.toString(num);
        exhaust_pile.setText(exhaust);
        exhaust_pile.repaint();
        System.out.println("GUI updating exhaust num " + exhaust + ".");
    }

    // 没有烧牌堆更新。在外部更新？
    // @Test for test
    public static void main(String[] args) {
        CustomFrame frame = new CustomFrame();
        GUI gui = new GUI(frame);
        frame.revalidate();
        frame.repaint();
        gui.setMainCaption("Test Word 1", 2);
        // frame.pack();
    }
}

/*
    GUI元素:
        1 矩形 代表生命条 (HP: 100/100)
        1 圆形 代表能量 (3/3)
        2 矩形 代表抽牌弃牌堆 (Deal Pile)  (Discard Pile)
        5 矩形 代表手牌 (Hand Card 1-5)
        3或4 button (Play Selected Card) (End turn) (Deck) (Option)
 */