package rings;

import actions.AbstractGameAction;
import actions.RelicAboveCreatureAction;
import actions.common.DrawCardAction;
import core.AbstractCreature;
import dungeons.AbstractDungeon;

public class Jormungandr extends AbstractRing {
    public static final String ID = "Jormungandr";
    private boolean canDraw = false;
    private boolean disabledUntilEndOfTurn = false;

    public Jormungandr() {
        super("ID", "", RingTier.RARE, LandingSound.CLINK);
    }

    public void atPreBattle() {
        this.canDraw = false;
    }

    public void atTurnStart() {
        this.canDraw = true;
        this.disabledUntilEndOfTurn = false;
    }

    public void disableUntilTurnEnds() {
        this.disabledUntilEndOfTurn = true;
    }

    public void onRefreshHand() {
        if (
                AbstractDungeon.actionManager.actions.isEmpty() &&
                        AbstractDungeon.onStagePlayer.hand.isEmpty() &&
                        !AbstractDungeon.actionManager.turnHasEnded &&
                        this.canDraw
            // && !AbstractDungeon.onStagePlayer.hasPower("No Draw")
            // && !AbstractDungeon.isScreenUp
        ){
            if (
                    // (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT
                    // && !this.disabledUntilEndOfTurn &&
                    (AbstractDungeon.onStagePlayer.discardPile.size() > 0 || AbstractDungeon.onStagePlayer.drawPile.size() > 0)) {
                    // if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT
                    // && !this.disabledUntilEndOfTurn
                    // && (AbstractDungeon.player.discardPile.size() > 0 || AbstractDungeon.player.drawPile.size() > 0))

                // flash();

                // top first, bot last
                AbstractGameAction act1 = new RelicAboveCreatureAction(AbstractDungeon.onStagePlayer, this);
                AbstractDungeon.actionManager.addToBottom(act1);
                AbstractGameAction act2 = new DrawCardAction(AbstractDungeon.onStagePlayer, 1);
                AbstractDungeon.actionManager.addToTop(act2);
            }
        }
    }

    public AbstractRing makeCopy() {
        return new Jormungandr();
    }

}

//     public class UnceasingTop extends AbstractRelic {
//         /*    */   public static final String ID = "Unceasing Top";
//         /*    */   private boolean canDraw = false;
//         /*    */   private boolean disabledUntilEndOfTurn = false;
//
//         /*    */
//         /*    */
//         public UnceasingTop() {
//             /* 16 */
//             super("Unceasing Top", "top.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
//             /*    */
//         }
//
//         /*    */
//         /*    */
//         /*    */
//         public String getUpdatedDescription() {
//             /* 21 */
//             return this.DESCRIPTIONS[0];
//             /*    */
//         }
//
//         /*    */
//         /*    */
//         /*    */
//         public void atPreBattle() {
//             /* 26 */
//             this.canDraw = false;
//             /*    */
//         }
//
//         /*    */
//         /*    */
//         /*    */
//         public void atTurnStart() {
//             /* 31 */
//             this.canDraw = true;
//             /* 32 */
//             this.disabledUntilEndOfTurn = false;
//             /*    */
//         }
//
//         /*    */
//         /*    */
//         public void disableUntilTurnEnds() {
//             /* 36 */
//             this.disabledUntilEndOfTurn = true;
//             /*    */
//         }
//
//         /*    */
//         /*    */
//         /*    */
//         public void onRefreshHand() {
//             /* 41 */
//             if (
//                     AbstractDungeon.actionManager.actions.isEmpty()
//                             && AbstractDungeon.onStagePlayer.hand.isEmpty()
//                             && !AbstractDungeon.actionManager.turnHasEnded
//                             && this.canDraw
//                 // && !AbstractDungeon.onStagePlayer.hasPower("No Draw")
//                 // && !AbstractDungeon.isScreenUp
//             ) {
//                 /* 44 */
//                 if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !this.disabledUntilEndOfTurn && (
//                         AbstractDungeon.player.discardPile.size() > 0 || AbstractDungeon.player.drawPile.size() > 0)) {
//                     flash();
//                     addToTop((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
//                     addToBot((AbstractGameAction) new DrawCardAction((AbstractCreature) AbstractDungeon.player, 1));
//                 }
//             }
//         }
//
//     }


/* Location:              E:\Slay the Spire\Slay the Spire\desktop-1.0.jar!\com\megacrit\cardcrawl\relics\UnceasingTop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */