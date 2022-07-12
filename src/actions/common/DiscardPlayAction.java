package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

// 弃掉打出的牌（而非从手牌中弃掉
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
        if (targetCard == null) {
            return;
        }
        // animation
        this.source.moveToDiscardPile(targetCard);
        // System.out.println("Just DiscardPlayAction, disc pile "+this.source.discardPile.size());
        // AbstractGUI.reLayoutHand();
    }

}
