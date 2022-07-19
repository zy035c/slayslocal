package actions.common;


import buff.AbstractBuff;
import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import dungeons.AbstractDungeon;

import java.util.Collections;

public class ApplyBuffAction extends AbstractGameAction {
    private AbstractBuff buffToApply;

    public ApplyBuffAction(AbstractCreature target, AbstractCreature source, AbstractBuff buffToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {

        setValues(target, source, stackAmount);
        this.buffToApply = buffToApply;

//        if (AbstractDungeon.onStagePlayer.hasRelic("Snake Skull") && source != null && source.isPlayer && target != source && buffToApply.ID.equals("Poison")) {
//            AbstractDungeon.onStagePlayer.getRelic("Snake Skull").flash();
//            this.buffToApply.amount++;
//            this.amount++;
//        }
        if (buffToApply.ID.equals("Corruption")) {
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
        }
        this.actionType = AbstractGameAction.ActionType.POWER;
        this.attackEffect = effect;
        if (AbstractDungeon.areEnemiesBasicallyDead()) {
            this.isDone = true;
        }
    }

    public ApplyBuffAction(AbstractCreature target, AbstractCreature source, AbstractBuff buffToApply, int stackAmount, boolean isFast) {
        this(target, source, buffToApply, stackAmount, isFast, AbstractGameAction.AttackEffect.NONE);
    }

    public ApplyBuffAction(AbstractCreature target, AbstractCreature source, AbstractBuff buffToApply) {
        this(target, source, buffToApply, buffToApply.amount);
    }

    public ApplyBuffAction(AbstractCreature target, AbstractCreature source, AbstractBuff buffToApply, int stackAmount) {
        this(target, source, buffToApply, stackAmount, false, AbstractGameAction.AttackEffect.NONE);
    }

    public ApplyBuffAction(AbstractCreature target, AbstractCreature source, AbstractBuff buffToApply, int stackAmount, AbstractGameAction.AttackEffect effect) {
        this(target, source, buffToApply, stackAmount, false, effect);
    }


    //
    //
    //
    //
    public void update() {
        if (this.target == null || this.target.isDeadOrEscaped()) {
            this.isDone = true;
            return;
        }

        if (this.buffToApply instanceof buff.NoDrawBuff && this.target.hasBuff(this.buffToApply.ID)) {
            this.isDone = true;
            return;
        }

        if (this.source != null) {
            for (AbstractBuff buff : this.source.buffs) {
                buff.onApplyBuff(this.buffToApply, this.target, this.source);
            }
        }

        // Whenever you apply Vulnerable, also apply 1 Weak.
        if (AbstractDungeon.onStagePlayer.hasRing("Overlord Ring") &&
                this.source != null && this.source.isPlayer &&
                this.target != this.source &&
                this.buffToApply.ID.equals("Vulnerable") &&
                !this.target.hasBuff("Pulp")) {
            AbstractDungeon.onStagePlayer.getRing("Champion Belt").onTrigger(this.target);
        }

        if (// this.target instanceof AbstractMonster &&
                this.target.isDeadOrEscaped()) {
            this.isDone = true;
            return;
        }

        // Relic: Ginger
        // Relic: Turnip

        //
        if (this.target.hasBuff("Pulp") &&
                this.buffToApply.type == AbstractBuff.BuffType.DEBUFF) {
            // addToTop((AbstractGameAction) new TextAboveCreatureAction(this.target, TEXT[0]));
            // animation & sound
            this.target.getBuff("Pulp").onSpecificTrigger();
            return;
        }

        boolean hasBuffAlready = false;
        for (AbstractBuff b : this.target.buffs) {
            if (b.ID.equals(this.buffToApply.ID)) { // && Night Terror
                b.stackPower(this.amount);
                // b.flash();

                // if amount <= 0 ...
                // if amount > 0 ...

                hasBuffAlready = true;
                // AbstractDungeon.onModifyPower();
            }
        }

        if (this.buffToApply.type == AbstractBuff.BuffType.DEBUFF) {
            // this.target.useFastShakeAnimation(0.5F);
        }

        if (!hasBuffAlready) {
            this.target.buffs.add(this.buffToApply);
            Collections.sort(this.target.buffs);
            this.buffToApply.onInitialApplication();
            // this.buffToApply.flash();
            // AbstractDungeon.onModifyPower();

            if (this.target.isPlayer) {
                int buffCount = 0;
                for (AbstractBuff p : this.target.buffs) {
                    if (p.type == AbstractBuff.BuffType.BUFF) {
                        buffCount++;
                    }
                }
                if (buffCount >= 10) {
                    // 解锁成就。。。
                }
            }
        }
    }
}


