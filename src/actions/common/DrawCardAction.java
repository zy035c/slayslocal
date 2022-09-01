package actions.common;
import actions.AbstractGameAction;
import core.AbstractCreature;
import cards.AbstractCard;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

import java.util.ArrayList;


// always draw to onStagePlayer
public class DrawCardAction extends AbstractGameAction{
    public ArrayList<AbstractCard> drawnCards = new ArrayList<>();
    private boolean clearDrawHistory = true;

    public DrawCardAction(AbstractCreature source, int amount, boolean endTurnDraw) {
        if (endTurnDraw) {
            // pass
        }
        setValues(AbstractDungeon.onStagePlayer, source, amount);
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

        try {
            AbstractPlayer p = (AbstractPlayer) source;
            if (p.hasBuff("NoDraw")) {
                // messagebox
                return;
            }

            if (this.amount <= 0) {
                // endActionWithFollowUp();
                return;
            }

            int drawSize = p.drawPile.size();
            int discardSize = p.discardPile.size();

            if (drawSize + discardSize <= 0) {
                // endActionWithFollowUp();
                return;
            }

            if (p.hand.size() >= p.hand_max) {
                // AbstractDungeon.onStagePlayer.createHandIsFullDialog();
                // endActionWithFollowUp();
                // messagebox hand is full
                return;
            }

            // 把是否需要洗牌的判断放到AbstractPlayer里面。
            // 重新从外部添加洗牌action
            drawnCards = p.drawAndPeek(amount);
            isDone = true;
            // AbstractGUI.reLayoutHand();
        } catch (ClassCastException e) { e.printStackTrace(); }
    }

}
