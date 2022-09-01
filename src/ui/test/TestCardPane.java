package ui.test;

import cards.AbstractCard;
import cards.common.Strike;

import ui.GUI;
import ui.GameColors;
import ui.listeners.TestListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;


/******************************************************************************
 * 测试类
 ******************************************************************************/

public class TestCardPane extends JLayeredPane {

    private int originalX, originalY;
    public static int CARD_WIDTH = GUI.calcX(180);
    public static int CARD_HEIGHT = GUI.calcY(300);
    public AbstractCard card;

    public TestCardPane(AbstractCard card, int cardX, int cardY) {
        originalX = cardX;
        originalY = cardY;
        // this.setBorder(b);

        this.card = card;

        this.setFont(new Font("Inter", Font.PLAIN, 24));
        this.setOpaque(true);
        this.setBounds(cardX, cardY, CARD_WIDTH, CARD_HEIGHT);

        TestListener listener = new TestListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);

        initCardPane();
    }

    // 166, 676
    // 376
    // 594
    // 832
    // 1066

    JLabel cardTitle;
    JLabel cardImage;
    JLabel description;

    private int titleWidth;
    private int titleHeight;
    private int descriptWidth;
    private int descriptHeight;
    private final int border_width = CARD_HEIGHT / 20;
    // title-descript-seperation
    private final int titleDescrSeperate = (int)(border_width * 0.9);

    // 相对位置
    private void initComponentSize() {
        titleWidth = CARD_WIDTH - border_width * 2;
        titleHeight = CARD_HEIGHT / 8;

        descriptWidth = CARD_WIDTH - 4 * border_width;
        descriptHeight = (int)(CARD_HEIGHT / 3);
    }

    private void initCardPane() {
        initComponentSize();

        // 根据卡的颜色选择颜色（哪个角色的卡
        Color card_col = GameColors.card_colorless;
        Color descript_col = GameColors.card_colorless2;
        switch (this.card.color) {
            case RED:
                card_col = GameColors.card_red;
                descript_col = GameColors.card_red5;
                break;
            case COLORLESS:
                // do nothing
                break;
            case GREEN:
                //
                break;
        }

        // 根据卡的稀有度选择标题颜色（
        Color title_col = GameColors.card_common;
        switch (this.card.rarity) {
            case COMMON:
                //
                break;
            case UNCOMMON:
                title_col = GameColors.card_uncommon;
                break;
            case RARE:
                title_col = GameColors.card_rare;
        }

        // 设置整体
        this.setBackground(card_col); // 设置整体颜色
        EtchedBorder eb = new EtchedBorder(EtchedBorder.LOWERED); // 最外层border
        LineBorder lineBorder = new LineBorder(card_col, border_width); // 包含cost的line border
        String cost = this.card.getCost();
        TitledBorder costBorder = new TitledBorder(lineBorder, cost); // title border里写上cost的string
        Font costFont = new Font("Roman", Font.PLAIN, 18); // cost的字体。
        costBorder.setTitleFont(costFont);
        costBorder.setTitleColor(Color.WHITE); // 如果是modified过，蓝色字
        CompoundBorder cardExBorder = new CompoundBorder(eb, costBorder); // 合成compound
        BorderLayout borderLayout = new BorderLayout(border_width, border_width); // 采用border layout
        this.setBorder(cardExBorder);
        this.setLayout(borderLayout);

        // 设置标题
        cardTitle = new JLabel("", SwingConstants.CENTER);
        Font titleFont = new Font("Dialog", Font.ITALIC, 16);
        cardTitle.setFont(titleFont);
        cardTitle.setOpaque(true);
        cardTitle.setBackground(title_col);
        cardTitle.setForeground(Color.white);
        cardTitle.setText(this.card.NAME);
        cardTitle.setSize(titleWidth, titleHeight);
        cardTitle.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        // cardTitle.setLocation(border_width,border_width);
        add(this.cardTitle, BorderLayout.NORTH);


        // 设置描述部分
        description = new JLabel();
        // @Test
        String testString = "Gain 5 Block.\nUpgrade ALL your cards.\nExhaust.";
        description.setForeground(Color.WHITE); // 字体颜色
        description.setBackground(descript_col); // 背景颜色
        description.setLayout(new FlowLayout()); // 流式布局
        description.setAlignmentY(Component.CENTER_ALIGNMENT); //
        // description.setEditable(false); // 不可编辑
        description.setFocusable(false); // 不可加光标
        // description.setDragEnabled(false); // 里面的文字不可以被拖动

        // 加入LayeredPane的监听器。使得被拖动时call那个监听器
        description.addMouseListener(this.getMouseListeners()[0]);
        description.addMouseMotionListener(this.getMouseMotionListeners()[0]);

        description.setFont(new Font("", Font.PLAIN, 12));
        // 关键字标黄：之后来慢慢调整
//        StyledDocument doc = description.getStyledDocument();
//        SimpleAttributeSet center = new SimpleAttributeSet();
//        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
//        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        testString = "<html><div align=\"center\" style=\"font-size: 7px\" >" + testString + "</html>";
        // description.setContentType("text/html");
        // testString = " \n" + testString; // 强制换掉第一行

        description.setVerticalAlignment(SwingConstants.CENTER);
        description.setHorizontalAlignment(SwingConstants.CENTER);
        description.setText(testString);

        description.setPreferredSize(new Dimension(
                descriptWidth,
                descriptHeight
        )); // 设置大小
        // not sure if really works
        // 在整个pane的BorderLayout下，这个TextArea处于SOUTH位置，根据HEIGHT自动适配location
        description.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED)); // 加上内陷的border
        add(description, BorderLayout.SOUTH);
        description.repaint();
        moveBack();

        // 加上image
        cardImage = new JLabel();
        cardImage.setBackground(card_col);
        // 之后来慢慢调整
        cardImage.setSize(descriptWidth, descriptHeight);
        LineBorder imageLineBorder = new LineBorder(title_col, 5);
        TitledBorder imageBorder = new TitledBorder(
                imageLineBorder,
                this.card.type.toString()
        );
        Font typeFont = new Font("ROMAN", Font.PLAIN, 10);
        imageBorder.setTitleColor(Color.WHITE);
        imageBorder.setTitlePosition(TitledBorder.BOTTOM);
        imageBorder.setTitleFont(typeFont);
        cardImage.setBorder(imageBorder);
        this.add(cardImage, BorderLayout.CENTER);

        System.out.println("Generated one card label.");
        System.out.println("Generated at x " + originalX);

        this.setVisible(true);
    }

    public void moveTo(int x, int y) {
        this.setLocation(x, y);
        // 是相对位置！！而且自动一起移动
    }

    public void moveBack() {
        moveTo(originalX, originalY);
    }


    /******************************************************************************
     * 以下是交互的方法。
     *
     ******************************************************************************/

    // 这可谓是阴险的小把戏啊
    // 必须使用(Event evt, int x, int y)
    // 才能有效的overrides deprecated method in java.awt.Component


    public boolean checkPlayableOnClick() {
        if (!card.canPlay()) {
            if (!card.hasEnoughEnergy()) {
                // messagebox: not enough energy
            }
            // messagebox: cannot play
            return false;
        }
        return true;
    }

    public void destroy() {
        this.setVisible(false);
        this.setEnabled(false);
    }

    public Rectangle getRect() {
        return new Rectangle(
                getX(),
                getY(),
                CARD_WIDTH,
                CARD_HEIGHT
        );
    }

    public boolean onSelfPlayArea() {
        return this.getRect().intersects(GUI.getPlayArea());
    }

    public void updateDescript() {
        this.description.setText(this.card.DESCRIPTION);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        TestCardPane pane = new TestCardPane(
                new Strike(),
                50,
                50
        );
        frame.add(pane);
        frame.setVisible(true);
    }
}
