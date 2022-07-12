package actions.unique;

import actions.AbstractGameAction;
import cards.AbstractCard;
import cards.Deck;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class ApotheosisAction extends AbstractGameAction {

    public void update() {
        // if (this.duration == Settings.ACTION_DUR_MED) { } // animation?
        AbstractPlayer p = AbstractDungeon.onStagePlayer;
        upgradeAllCardsInDeck(p.hand);
        upgradeAllCardsInDeck(p.drawPile);
        upgradeAllCardsInDeck(p.discardPile);
        upgradeAllCardsInDeck(p.exhaustPile);
        this.isDone = true;
    }

    private void upgradeAllCardsInDeck(Deck cardDeck) {
        for (AbstractCard c: cardDeck.deckCards) {
            if (c.canUpgrade()) {
                if (cardDeck.deck_type == Deck.DECK_TYPE.HAND) {
                    // c.superFlash();
                    // 我不明白
                }
                c.upgrade();
                // c.applyPowers(); ?
            }
        }
    }
}