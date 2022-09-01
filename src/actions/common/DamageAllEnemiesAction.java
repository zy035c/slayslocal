package actions.common;

import actions.AbstractGameAction;
import cards.DamageInfo;
import core.AbstractCreature;
import dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DamageAllEnemiesAction extends AbstractGameAction {

    public int[] damage;
    private int baseDamage;
    private boolean firstFrame = true, utilizeBaseDamage = false;

    @Deprecated
    public DamageAllEnemiesAction(
            AbstractCreature source, int[] amount, DamageInfo.DamageType type,
            AbstractGameAction.AttackEffect effect, boolean isFast
    ) {
        this.source = source;
        this.damage = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        if (isFast) {
            // ...
        }
    }

    public DamageAllEnemiesAction(
            AbstractCreature source, int amount, DamageInfo.DamageType type,
            AbstractGameAction.AttackEffect effect
            ) {
        this.source = source;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.amount = amount;
    }

    @Override
    public void update() {
        ArrayList<AbstractCreature> targets = AbstractDungeon.getEnemies(this.source);
        if (targets == null) {
            throw new NullPointerException();
        }
        for (AbstractCreature creature: targets) {
            AbstractDungeon.actionManager.queueJump(new DamageAction(creature, new DamageInfo(this.source, this.amount, this.damageType)));
        }
    }
}
