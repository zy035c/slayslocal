package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class DiscardAtEndOfTurnAction extends AbstractGameAction {

    public void update() {
        // if (this.duration == DURATION) {
        // 这里是用一个ArrayList自带的Iterator来遍历
        // deckCards是一个Deck用来储存牌的ArrayList<AbstractCard>
        for (Iterator<AbstractCard> c = AbstractDungeon.onStagePlayer.hand.deckCards.iterator(); c.hasNext(); ) {
            AbstractCard e = c.next();
            if (e.retain || e.selfRetain) {
                AbstractDungeon.onStagePlayer.limbo.addToTop(e);
                c.remove();
            }
        }
        // 不明白RestoreRetainedCardsAction是干什么的
        // addToBot((AbstractGameAction) new RestoreRetainedCardsAction(AbstractDungeon.onStagePlayer.limbo));

        // addToBot还是addToBot？
        if (!AbstractDungeon.onStagePlayer.hasRing("Torch Runic Ring") &&
                !AbstractDungeon.onStagePlayer.hasBuff("Equilibrium"))
        // 如果有 符文金字塔（火把符文戒指） 或者 Equilibrium 本回合不弃牌
        {
            int tempSize = AbstractDungeon.onStagePlayer.hand.size();
            for (int i = 0; i < tempSize; i++) {
                addToTop(new DiscardAction(AbstractDungeon.onStagePlayer, null, AbstractDungeon.onStagePlayer.hand.size(), true, true));
            }
        }
        ArrayList<AbstractCard> cards = (ArrayList<AbstractCard>)AbstractDungeon.onStagePlayer.hand.deckCards.clone();

        Collections.shuffle(cards);
        for (AbstractCard abstractCard : cards) {
            abstractCard.triggerOnEndOfPlayerTurn();
        }
        this.isDone = true;
    }

}