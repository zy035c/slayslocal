package rings;

import actions.AbstractGameAction;
import actions.common.RingAboveCreatureAction;
import actions.common.DrawCardAction;
import dungeons.AbstractDungeon;

public class WoodenSculptureRing extends AbstractRing {
    public static final String name = "WoodenSculptureRing";
    public static final String ID = "WoodenSculptureRing";

    private boolean disabledUntilEndOfTurn = false;
    private String description = "Your first attack each combat deals 8 additional damage.";

    public WoodenSculptureRing() {
        super(name,ID, "", RingTier.COMMON, LandingSound.CLINK);
        this.description = description;
    }

//    public void atBattleStart() {
//        AbstractDungeon.onStagePlayer.
//    }


    public void disableUntilTurnEnds() {
        this.disabledUntilEndOfTurn = true;
    }


    public AbstractRing makeCopy() {
        return new WoodenSculptureRing();
    }

}
