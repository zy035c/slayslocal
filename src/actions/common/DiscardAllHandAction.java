package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;

public class DiscardAllHandAction extends AbstractGameAction {

    public DiscardAllHandAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
    }
    public DiscardAllHandAction(AbstractCreature target, AbstractCreature source) {
        this(target, source, 1);
    }

    // at end of the turn, discard all cards
    public DiscardAllHandAction(AbstractCreature target) {
        this(target, target);
    }

    public void update() {
        AbstractPlayer p = (AbstractPlayer)this.target;
        if (p.hand.size() <= 0) {
            return;
        }
        for (AbstractCard c: p.hand.deckCards) {
            p.moveToDiscardPile(c);
        }
        p.hand.clear();
        // AbstractGUI.reLayoutHand();
    }

}
