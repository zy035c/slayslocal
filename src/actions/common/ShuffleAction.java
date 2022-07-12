package actions.common;

import actions.AbstractGameAction;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class ShuffleAction extends AbstractGameAction {


    public ShuffleAction(AbstractPlayer p) {
        setValues(p, p, 0);
        this.actionType = ActionType.SHUFFLE;
    }

    public void update() {
        // animation
        AbstractDungeon.onStagePlayer.reshuffle();
    }

}
