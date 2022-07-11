package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class DiscardAction extends AbstractGameAction {
    public boolean isRandom;
    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        setValues(target, source, amount);
        this.isRandom = isRandom;
    }

    // self discard
    public DiscardAction(AbstractCreature target, int amount) {
        this(target, target, amount, false);
    }

    public void update() {
        AbstractPlayer p = (AbstractPlayer) this.target;

        if (AbstractDungeon.areEnemiesBasicallyDead()) {
            return;
        }

        if (p.hand.size() <= 0) {
            return;
        }

        if (p.hand.size() <= this.amount) {
            this.amount = p.hand.size();
            int tmp = p.hand.size();

            for (int i = 0; i < tmp; i++) {
                AbstractCard c = p.hand.drawFromTop();
                p.moveToDiscardPile(c);
                if (!this.endTurn) {
                    //
                }
            }

        } else {
            // SHOULD BE selected
            for (int i = 0; i < amount; i++) {
                AbstractCard c = p.hand.drawFromTop();
                p.moveToDiscardPile(c);
            }
        }
        // AbstractGUI.reLayoutHand();
    }

}
