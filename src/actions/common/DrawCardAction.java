package actions.common;
import actions.AbstractGameAction;
import core.AbstractCreature;
import cards.AbstractCard;
import dungeons.AbstractDungeon;

import java.util.ArrayList;


// always draw to onStagePlayer
public class DrawCardAction extends AbstractGameAction{
    public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();
    private boolean clearDrawHistory = true;

    public DrawCardAction(AbstractCreature source, int amount, boolean endTurnDraw) {
        if (endTurnDraw) {
            // pass
        }
        setValues((AbstractCreature) AbstractDungeon.onStagePlayer, source, amount);
        this.actionType = AbstractGameAction.ActionType.DRAW;
    }

    public DrawCardAction(AbstractCreature source, int amount) {
        this(source, amount, false);
    }

    public void update() {
        if (this.clearDrawHistory) {
            this.clearDrawHistory = false;
            // drawnCards.clear();
        }

//        if (AbstractDungeon.player.hasPower("No Draw")) {
//            AbstractDungeon.player.getPower("No Draw").flash();
//            endActionWithFollowUp();
//
//            return;
//        }
        if (this.amount <= 0) {
            // endActionWithFollowUp();
            return;
        }

        int deckSize = AbstractDungeon.onStagePlayer.drawPile.size();
        int discardSize = AbstractDungeon.onStagePlayer.discardPile.size();

        if (deckSize + discardSize <= 0) {
            // endActionWithFollowUp();
            return;
        }

        if (AbstractDungeon.onStagePlayer.hand.size() == 10) {
            // AbstractDungeon.onStagePlayer.createHandIsFullDialog();
            // endActionWithFollowUp();
            return;
        }

        if (deckSize < amount) {
            AbstractDungeon.onStagePlayer.reshuffle();
        }
        AbstractDungeon.onStagePlayer.draw(amount);
        // AbstractGUI.reLayoutHand();

    }

}
