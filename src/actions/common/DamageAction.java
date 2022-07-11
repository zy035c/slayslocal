package actions.common;

import actions.AbstractGameAction;
import dungeons.AbstractDungeon;
import cards.DamageInfo;
import core.AbstractCreature;

public class DamageAction extends AbstractGameAction {

    private DamageInfo info;
    public DamageAction(AbstractCreature target, DamageInfo info) {
        setValues(target, info);
        this.info = info;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }
    public void update() {
        this.target.damage(this.info);
    }


}
