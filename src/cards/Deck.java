package cards;

import java.util.ArrayList;
import java.util.*;

import cards.common.*;
import cards.red.Anger;
import cards.red.Clash;

public class Deck {

    public ArrayList<AbstractCard> deckCards = new ArrayList<AbstractCard>();;



    public enum DECK_TYPE {
        DRAW_PILE, DISCARD_PILE, HAND, MASTER_DECK, EXHAUST_PILE, LIMBO;
    }
    // LIMBO是回合结束后保留到下回合到牌的牌堆

    public DECK_TYPE deck_type;
    // only strike and defense in the deck
    public Deck(DECK_TYPE deck_type) {
        deckCards = new ArrayList<AbstractCard>();
        this.deck_type = deck_type;
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
                case "Anger":
                    this.addToTop(new Anger());
                default:
            }
        }

    }

    public void addToTop(AbstractCard c) {
        deckCards.add(c);
    }

    public void addToBottom(AbstractCard c) {
        Deck nd = new Deck(this.deck_type);
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

    public AbstractCard getRandomCard() {
        if (this.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        // 随机数如果是nextInt(25)，0-24
        return this.deckCards.get(rand.nextInt(size()));
    }

    public void removeSpecificCard(AbstractCard c) {
        if (!this.deckCards.contains(c)) {
            //...
            System.out.println("Removing non-existing card "+c.NAME+ " from "+this.deck_type.toString());
            return;
        }
        this.deckCards.remove(c);
    }

    public void clear() {
        this.deckCards.clear();
    }

    public Deck makeCopy() {
        Deck nd = new Deck(this.deck_type);
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

