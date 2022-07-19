package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import dungeons.AbstractDungeon;

public class UseCardAction extends AbstractGameAction {

    private AbstractCard targetCard;
    public AbstractCreature target = null;

    public boolean exhaustCard;
    public boolean returnToHand;

    // 不明确是干什么的
    public UseCardAction(AbstractCard card, AbstractCreature target) {
        this.targetCard = card;
        this.target = target;

//        if (card.exhaustOnUseOnce || card.exhaust) {
//            this.exhaustCard = true;
//        }

        setValues(target, AbstractDungeon.onStagePlayer, 1);
    }

    public void update() {
        this.targetCard.freeToPlayOnce = false;
        this.targetCard.isInAutoplay = false;
    }
}
