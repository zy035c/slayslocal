package ui;

import cards.AbstractCard;
import dungeons.DungeonScene;
import ui.GUI;
import ui.GameColors;
import ui.listeners.CardPaneListener;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    private int originalX, originalY;
    public AbstractCard card;
    public Point origin;
    public GUI gui;
    public DungeonScene scene;
    public static int CARD_WIDTH = GUI.calcX(180);
    public static int CARD_HEIGHT = GUI.calcY(300);

    public CustomLabel(AbstractCard card, int cardX, int cardY, DungeonScene scene) {

        this.setFont(new Font("Inter", Font.PLAIN, 24));
        this.setOpaque(true);
        this.setBackground(GameColors.grey1);
        this.setBounds(cardX, cardY, CARD_WIDTH, CARD_HEIGHT);
        this.setVisible(true);
        this.setEnabled(true);
        this.setText(card.NAME);

        originalX = cardX;
        originalY = cardY;
        origin = new Point(cardX, cardY);

        this.card = card;
        this.scene = scene;

//        CardPaneListener listener = new CardLabelListener(this, this.scene);
//        addMouseListener(listener);
//        addMouseMotionListener(listener);
    }

    public void moveBack() {
        this.setLocation(originalX, originalY);
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


//    public void paint(Graphics g) {
//        g.drawString(this.card.NAME, this.getX()+10, this.getY()+10);
//    }

}
