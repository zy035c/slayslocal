package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import dungeons.AbstractDungeon;

public class MakeTempCardInDiscardAction extends AbstractGameAction {
    private AbstractCard c;
    private int numCards;
    private boolean sameUUID;

    public MakeTempCardInDiscardAction(AbstractCard card, int amount) {
        // UnlockTracker.markCardAsSeen
        this.numCards = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        this.sameUUID = false;

    }

    public MakeTempCardInDiscardAction(AbstractCard card, boolean b) {
        this(card, 1);
        this.sameUUID = b;

        // if?
    }

    public void update() {
        // animation: show it
        if (numCards <= 0) {
            return;
        }
        if (numCards == 1) {
            AbstractDungeon.onStagePlayer.discardPile.addToTop(c);
            return;
        }

        // multi time animation: show at different place
        for (int i=0;i<numCards;i++) {
            // animation
            AbstractDungeon.onStagePlayer.discardPile.addToTop(c);
        }
    }

    private AbstractCard makeNewCard() {
        if (this.sameUUID) {
            //
            return null;
        }
        return this.c.makeStatEquivalentCopy();
    }
}
