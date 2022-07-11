package cards;

import java.util.ArrayList;
import java.util.*;
import cards.common.*;
import cards.red.Clash;

public class Deck {

    public ArrayList<AbstractCard> deckCards = new ArrayList<AbstractCard>();;

    // only strike and defense in the deck
    public Deck() {
        deckCards = new ArrayList<AbstractCard>();;
    }

    public Deck(ArrayList<String> list) {
        deckCards = new ArrayList<AbstractCard>();
        for (String name: list) {
            switch (name) {
                case "Strike":
                    this.addToTop(new Strike());
                    break;
                case "Defend":
                    this.addToTop(new Defend());
                    break;
                case "Clash":
                    this.addToTop(new Clash());
                    break;
                default:
            }
        }

    }

    public void addToTop(AbstractCard c) {
        deckCards.add(c);
    }

    public void addToBottom(AbstractCard c) {
        Deck nd = new Deck();
        nd.addToTop(c);
        for (AbstractCard ca: this.deckCards) {
            nd.addToTop(ca);
        }
        this.deckCards = nd.deckCards;
    }

    public void shuffle() {
        Collections.shuffle(deckCards);
    }

    public AbstractCard drawFromTop() {
        AbstractCard c = deckCards.get(deckCards.size()-1);
        deckCards.remove(c);
        return c;
    }

    public void clear() {
        this.deckCards.clear();
    }

    public Deck makecopy() {
        Deck nd = new Deck();
        for (AbstractCard c: deckCards) {
            nd.addToTop(c);
        }
        return nd;
    }

    public int size() {
        return deckCards.size();
    }


    public boolean isEmpty() {
        if (size() <= 0) {
            return true;
        }
        return false;
    }
}

