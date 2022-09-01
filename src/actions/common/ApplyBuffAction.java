package actions.common;


import buffs.AbstractBuff;
import actions.AbstractGameAction;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class ApplyBuffAction extends AbstractGameAction {
    private AbstractBuff buffToApply;

    public ApplyBuffAction(AbstractCreature target, AbstractCreature source, AbstractBuff buffToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
        setValues(target, source, stackAmount);
        this.buffToApply = buffToApply;
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
        this(target, source, buffToApply, buffToApply.stack);
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

        boolean take_effect = false;
        // 目标为空，不生效
        if (this.target == null || this.target.isDeadOrEscaped()) {
            this.isDone = true;
            return;
        }

        // 触发上buff来源的所有buff，看看有无可能发生什么
        if (this.source != null) {
            for (AbstractBuff buff : this.source.buffs) {
                buff.onApplyBuff(this.buffToApply, this.target, this.source);
            }
        }

        // 如果有pulp，并且此buff可以被取消：抵消这次上buff
        if (this.target.hasBuff("Pulp") &&
            this.buffToApply.cancellable) {
            // addToTop((AbstractGameAction) new TextAboveCreatureAction(this.target, TEXT[0]));
            // animation & sound
            this.target.getBuff("Pulp").onSpecificTrigger();
            take_effect = false;
            return;
        }

        // Relic: Ginger
        // Relic: Turnip

        boolean hasBuffAlready = false;
        for (AbstractBuff b : this.target.buffs) { // 如果已经有这个buff了：叠层
            if (b.ID.equals(this.buffToApply.ID)) { // && Night Terror
                if (b.stackable) { // 如果此buff不可叠层：不增加更多，但是仍然视为上了
                    if (this.amount > 0) {
                        b.incrementStack(amount);
                    } else {
                        b.decrementStack(amount);
                    }
                    // b.flash();
                }
                hasBuffAlready = true;
                take_effect = true;
                break;
                // AbstractDungeon.onModifyPower();
            }
        }

        if (!hasBuffAlready) {
            this.target.buffs.add(this.buffToApply);
            // Collections.sort(this.target.buffs); ?
            this.buffToApply.onInitialApplication();
            // this.buffToApply.flash();
            // AbstractDungeon.onModifyPower();
            take_effect = true;
            // 解锁成就
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

        if (this.buffToApply.type == AbstractBuff.BuffType.DEBUFF && take_effect) {
            // this.target.useFastShakeAnimation(0.5F);
        }

        if (this.source != null && this.source.isPlayer) {
            AbstractPlayer owner = (AbstractPlayer) this.source;
            // Whenever you apply Compromise, also apply 1 Weak.
            if (owner.hasRing("Overlord Ring") &&
                this.source.isPlayer &&
                this.target != owner &&
                this.buffToApply.ID.equals("Compromise") &&
                take_effect) {
                AbstractDungeon.onStagePlayer.getRing("Overlord Ring").onTrigger(this.target);
            }
        }


    }
}


