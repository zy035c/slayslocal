package actions;
import cards.DamageInfo;
import dungeons.AbstractDungeon;
import core.AbstractCreature;

/******************************************************************************
 *  GameAction的抽象父类，所有GameAction都必须extend这个类，并且实现update()方法。
 *  GameAction是一种游戏中的action，这样设计用来等待前端动画执行的时间。
 *  应该有一个during参数来决定动画执行时长。（暂无
 *
 ******************************************************************************/

public abstract class AbstractGameAction {

    public AbstractCreature target;
    public int amount;
    public ActionType actionType;
    public AbstractCreature source;
    public boolean endTurn = false;

    protected void setValues(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
    }

    protected void setValues(AbstractCreature target, DamageInfo info) {
        this.source = info.owner;
        this.target = target;
        this.amount = 0;
    }

    // 攻击/动作特效动画分类
    public enum AttackEffect
    {
        BLUNT_LIGHT,
        BLUNT_HEAVY,
        SLASH_DIAGONAL, SMASH,
        SLASH_HEAVY, SLASH_HORIZONTAL,
        SLASH_VERTICAL,
        NONE, FIRE, POISON,
        SHIELD, LIGHTNING;
    }

    // 动作的类型，作用尚不清楚
    public enum ActionType
    {
        BLOCK,
        POWER,
        CARD_MANIPULATION,
        DAMAGE, DEBUFF,
        DISCARD, DRAW, EXHAUST,
        HEAL, ENERGY, TEXT, USE,
        CLEAR_CARD_QUEUE, DIALOG,
        SPECIAL, WAIT, SHUFFLE,
        REDUCE_POWER;
    }

//    protected boolean isDeadOrEscaped(core.AbstractCreature target) {
//        if (target.isDying || target.halfDead) {
//            return true;
//        }
//        if (!target.isPlayer) {
//            AbstractMonster m = (AbstractMonster) target;
//            if (m.isEscaping) {
//                return true;
//            }
//        }
//        return false;
//    }

    public abstract void update();

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

}
