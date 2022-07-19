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

    public DiscardPlayAction(AbstractCreature target, AbstractPlayer source, AbstractCard card) {
        this.targetCard = card;
        this.target = target;
        this.source = source;
        setValues(target, source, 0);
    }

    //
    public DiscardPlayAction(AbstractCard card) {
        this(
                AbstractDungeon.onStagePlayer,
                AbstractDungeon.onStagePlayer,
                card
        );
    }

    public void update() {
        if (targetCard == null) {
            return;
        }
        // animation
        if (targetCard.exhaust) {
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(
                    AbstractDungeon.onStagePlayer,
                    AbstractDungeon.onStagePlayer,
                    targetCard
            ));
            AbstractDungeon.actionManager.executeAction(); // 递归调用，如果烧牌触发了其他action，以此类推
            return;
        }
        this.source.moveToDiscardPile(targetCard);
        // System.out.println("Just DiscardPlayAction, disc pile "+this.source.discardPile.size());
        // AbstractGUI.reLayoutHand();
    }

}
