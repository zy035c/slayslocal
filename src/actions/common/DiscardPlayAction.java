package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class DiscardPlayAction extends AbstractGameAction {
    AbstractCard targetCard;
    AbstractPlayer source;
    AbstractCreature target;

    public DiscardPlayAction(AbstractCard card, AbstractCreature target) {
        this.targetCard = card;
        this.target = target;
        this.source = AbstractDungeon.onStagePlayer;
        setValues(target, source, 0);
    }
    public DiscardPlayAction(AbstractCard card) {
        this(card, null);
    }

    public void update() {
        if (source.hand.size() <= 0) {
            return;
        }
        // animation
        this.source.discardFromHand(targetCard);
        // AbstractGUI.reLayoutHand();
    }

}
