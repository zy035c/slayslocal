package ui.window;

import cards.AbstractCard;
import cards.Deck;
import ui.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ui.window.CustomFrame.FRAME_WIDTH;

public class PileWindow extends JFrame {

    public PileWindow(Deck deck) {
        this.setTitle(deck.deck_type.name());
        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new GridLayout());

        for (AbstractCard card: deck.deckCards) {

        }
    }


}
