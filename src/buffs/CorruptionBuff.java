package buffs;

import cards.AbstractCard;
import dungeons.AbstractDungeon;

public class CorruptionBuff {


    public void onInitialApplication() {
        for (AbstractCard c : AbstractDungeon.onStagePlayer.hand.deckCards) {
            if (c.type == AbstractCard.CardType.SKILL)
                c.modifyCostForCombat(-9);
        }
        for (AbstractCard c : AbstractDungeon.onStagePlayer.drawPile.deckCards) {
            if (c.type == AbstractCard.CardType.SKILL)
                c.modifyCostForCombat(-9);
        }
        for (AbstractCard c : AbstractDungeon.onStagePlayer.discardPile.deckCards) {
            if (c.type == AbstractCard.CardType.SKILL)
                c.modifyCostForCombat(-9);
        }
        for (AbstractCard c : AbstractDungeon.onStagePlayer.exhaustPile.deckCards) {
            if (c.type == AbstractCard.CardType.SKILL) {
                c.modifyCostForCombat(-9);
            }
        }
//
    }
}
