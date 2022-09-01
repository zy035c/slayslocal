package ui.window;

import cards.AbstractCard;
import cards.Deck;

import javax.swing.*;
import java.awt.*;

public class PileWindow extends JFrame {


    public PileWindow(Deck deck) {
        String pileTitle = deck.deck_type.toString();
        this.setTitle(pileTitle);
        System.out.println("---> Open pile window. Type:" + pileTitle);

        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new GridLayout());

        for (AbstractCard card: deck.deckCards) {

        }
    }


}
