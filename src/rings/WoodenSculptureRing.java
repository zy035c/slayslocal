package rings;

import actions.AbstractGameAction;
import actions.common.RingAboveCreatureAction;
import actions.common.DrawCardAction;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class WoodenSculptureRing extends AbstractRing {
    public static final String name = "Wooden Sculpture Ring";
    public static final String ID = "WoodenSculptureRing";

    private boolean disabledUntilEndOfTurn = false;
    private static final String description = "Your first attack each combat deals 8 additional damage.";

    public WoodenSculptureRing(AbstractPlayer owner) {
        super(name,ID, description, "", RingTier.COMMON, owner, LandingSound.CLINK);
    }

//    public void atBattleStart() {
//        AbstractDungeon.onStagePlayer.
//    }


    public void disableUntilTurnEnds() {
        this.disabledUntilEndOfTurn = true;
    }


    public AbstractRing makeCopy() {
        return new WoodenSculptureRing(owner);
    }

}
