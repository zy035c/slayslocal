package actions;
import java.util.ArrayList;

/******************************************************************************
 *  有一个类似于queue的结构，public ArrayList<AbstractGameAction> actions
 *  GameActionManager会从top往bottom逐个执行GameAction.
 *  phase表示动画是正在执行还是执行完毕。在动画正在执行的时候前端不可交互。
 *  例如卡打出的特效执行完之前，暂时不能打出第二张牌。特效的执行也不能重叠发生，
 *  在队列中依次执行。一张卡引起的所有后续效果也是如此。
 ******************************************************************************/
public class GameActionManager {

    public boolean turnHasEnded;
    public static int damageReceivedThisTurn;
    public static int damageReceivedThisCombat;
    public ArrayList<AbstractGameAction> actions = new ArrayList<>();
    public AbstractGameAction currentAction = null;
    public Phase phase;

    public GameActionManager() {
        this.phase = Phase.WAITING_ON_USER;
    }

    // old top, new bottom
    // top的是后添加的，应该从bottom开始执行
    public void addToBottom(AbstractGameAction action) {

    }

    public void addToTop(AbstractGameAction action) {
        actions.add(action);
    }

    // 结束回合
    public void endTurn() {
        turnHasEnded = true;
        // actions.clear();
        // this.phase = Phase.WAITING_ON_USER;
    }

    public void startTurn() {
        turnHasEnded = false;
    }

    // 执行最bottom的一个动作，然后根据是否还有动作未执行来set this.phase
    public void executeAction() {

        // if currentAction.isDone == true
        if (actions.size() <= 0) {
            System.out.println("executeAction called but no action in queue.");
            this.phase = Phase.WAITING_ON_USER;
            return;
        }

        currentAction = actions.get(0);
        currentAction.update();

        System.out.println("executeAction: " + currentAction.getClass().getSimpleName());
        actions.remove(currentAction);

        if (actions.size() <= 0) {
            this.phase = Phase.WAITING_ON_USER;
        } else {
            this.phase = Phase.EXECUTING_ACTIONS;
        }
        System.out.println("Previous Action done. " + this.phase);
    }

    // 一直执行到清空队列为止
    public void emptyQueue() {
        if (this.actions.isEmpty()) {
            this.phase = Phase.WAITING_ON_USER;
            return;
        }
        this.phase = Phase.WAITING_ON_USER;
        while (actions.size() > 0) {
            executeAction();
        }
    }

    public enum Phase {
        WAITING_ON_USER, EXECUTING_ACTIONS;
    }

}
