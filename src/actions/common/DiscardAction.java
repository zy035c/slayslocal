package actions.common;

import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

// 被动弃牌的action
public class DiscardAction extends AbstractGameAction {
    public boolean isRandom;
    public boolean endTurn;

    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean endTurn) {
        setValues(target, source, amount);
        this.isRandom = isRandom;
        this.endTurn = endTurn;
    }

    // self discard
    public DiscardAction(AbstractCreature target, int amount) {
        this(target, target, amount, false, false);
    }
    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this(target, source, amount, isRandom, false);
    }

    public void update() {
        this.p = (AbstractPlayer) this.target;

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
                    c.triggerOnManualDiscard();
                }
                // GameActionManager.incrementDiscard(this.endTurn); 不明白含义
            }

            // AbstractDungeon.onStagePlayer.hand.applyBuffs(); // 不明白
            return;
        }

        if (isRandom) {
            for (int i=0; i<this.amount; i++) {
                AbstractCard c = p.hand.getRandomCard(/*AbstractDungeon.cardRandomRng*/); // 暂时没有RNG类
                this.p.moveToDiscardPile(c); // 在其中会触发onRefreshHand和onDrawOrDiscard
                c.triggerOnManualDiscard();
                // GameActionManager.incrementDiscard(this.endTurn);
            }
        } else {
            if (this.amount < 0) {
                // 我不明白为什么amount可以小于零
            }
            // numDiscarded = this.amount; // ?
            if (this.p.hand.size() > this.amount) {
                // AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
                // call前端开选牌窗口
            }
            // AbstractDungeon.player.hand.applyPowers();
            return;
        }
        // SHOULD BE selected

        // 应该是for card in selectedDeck, discard

        for (int i = 0; i < amount; i++) {
            AbstractCard c = p.hand.drawFromTop();
            p.moveToDiscardPile(c);
        }

        // AbstractGUI.reLayoutHand();
    }

}
