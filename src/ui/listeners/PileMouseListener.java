package ui.listeners;

import cards.Deck;
import ui.window.PileWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PileMouseListener extends MouseAdapter {

    Deck deck;

    // Constructor
    public PileMouseListener(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(deck.isEmpty()) {
            // message box is empty
            return;
        }
        PileWindow window = new PileWindow(deck);
    }
}
