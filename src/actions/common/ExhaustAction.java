package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class ExhaustAction extends AbstractGameAction {

    AbstractCard targetCard;

    public ExhaustAction(AbstractPlayer target, AbstractPlayer source, AbstractCard targetCard) {
        this.targetCard = targetCard;
        setValues(target, source, 1);
        this.actionType = ActionType.EXHAUST;
    }

    public ExhaustAction(AbstractCard targetCard) {
        this(AbstractDungeon.onStagePlayer,
                AbstractDungeon.onStagePlayer,
                targetCard
        );
    }

    @Override
    public void update() {
        try {
            AbstractPlayer p = (AbstractPlayer) this.target;
            p.exhaustCard(this.targetCard);
        } catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("WARNING: non-player is trying exhaust card.");
        }
    }
}
