package buff;

import actions.AbstractGameAction;
import core.AbstractCreature;
import dungeons.AbstractDungeon;
import rings.AbstractRing;

public abstract class AbstractBuff implements Comparable<AbstractBuff> {
    public static final String ID = "";
    public int amount;

    public BuffType type;

    public void stackPower(int amount) {
    }


    public enum BuffType {
        BUFF, DEBUFF;
    }

    public void onApplyBuff(AbstractBuff buff, AbstractCreature target, AbstractCreature source) {}
    public void onSpecificTrigger() {}
    public void onInitialApplication() {}

    protected void addToBot(AbstractGameAction action) { AbstractDungeon.actionManager.addToBottom(action); }
    protected void addToTop(AbstractGameAction action) {
        /* 123 */     AbstractDungeon.actionManager.addToTop(action);
        /*     */   }

    @Override
    public int compareTo(AbstractBuff buff) {
        return 0;
    }
}

