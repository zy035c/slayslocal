package actions.common;

import actions.AbstractGameAction;
import core.AbstractCreature;


public class GainBlockAction extends AbstractGameAction {

    public GainBlockAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.BLOCK;
    }
    public GainBlockAction(AbstractCreature target, int amount) {
        this(target, target, amount);
    }

    public void update() {
        this.target.addBlock(this.amount);
        // AbstractGUI.updateBlockBar(target);
    }

}
