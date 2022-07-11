package actions.common;

import actions.AbstractGameAction;
import dungeons.AbstractDungeon;

public class EndTurnAction extends AbstractGameAction {
    public void update() {
        AbstractDungeon.actionManager.endTurn();
        // if ()
    }
}
