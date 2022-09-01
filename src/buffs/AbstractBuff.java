package buffs;

import actions.AbstractGameAction;
import cards.DamageInfo;
import core.AbstractCreature;
import dungeons.AbstractDungeon;

public abstract class AbstractBuff implements Comparable<AbstractBuff> {
    public String ID = "";
    public String NAME = "";
    public int stack;
    private String description = "";
    private String IMG_PATH = "";

    public boolean stackable; // 是否可以叠层
    public boolean cancellable; // 是否可以被Pulp抵消

    public boolean loseAllOnNextPlay = false;
    public boolean loseOnNextPlay = false;
    public boolean gainOnNextPlay = false;

    public boolean loseAtEndTurn = false;
    public boolean loseAllAtEndTurn = false;
    public boolean gainAtEndTurn = false;


    public boolean modifyDamage = false; // 这个buff是否可以更改伤害 重写atDamageReceive方法

    public boolean negativity = false; // 是否可以为负

    public AbstractCreature owner;

    public BuffType type;

    AbstractBuff(String id, String name, String IMG_PATH, AbstractCreature owner, int stack,
                 String description, boolean stackable, boolean cancellable) {
        this.ID = id;
        this.NAME = name;
        this.IMG_PATH = IMG_PATH;
        this.stack = stack;
        this.owner = owner;
        this.description = description;
        this.stackable = stackable;
        this.cancellable = cancellable;
    }

    public void incrementStack(int amount) {
        if (stackable) {
            assert amount >= 0;
            if (this.stack + amount <= 99) {
                this.stack += amount;
            }
        }
    }

    // 等待override
    public void decrementStack(int amount) {
        assert amount <= 0;
        if (stackable) {
            if (this.stack + amount == 0) {
                this.owner.removeBuff(this);
                return;
            }
            if (!negativity & this.stack + amount < 0) {
                this.owner.removeBuff(this);
                return;
            }
            this.stack += amount;
        }
    }

    public void onApplyBuff(AbstractBuff buff, AbstractCreature target, AbstractCreature source) {} // 触发上buff来源的所有buff，看看有无可能发生什么
    public void onSpecificTrigger() {}
    public void onInitialApplication() {}

    public boolean modifyDamageReceive = false;
    public int atDamageReceive(DamageInfo info, int damageAmount) {return -1;} // 必须override
    public boolean modifyDamageGive = false;
    public int atDamageGive(float damage, DamageInfo.DamageType type) {return -1;} // 必须override

    public void atEndOfTurn() {
        if (loseAtEndTurn && stackable) {
            if (loseAllAtEndTurn) {
                this.stack = 0;
                this.owner.removeBuff(this);
            }
            this.decrementStack(-1);
        }
    }

    public String getDescription() {
        String descript = this.description.replace(
                "!AMOUNT!",
                Integer.toString(this.stack)
        );
        return descript;
    }

    public String getStackString() {
        if (!stackable) {
            return "";
        }
        return Integer.toString(this.stack);
    }

    protected void addToBot(AbstractGameAction action) { AbstractDungeon.actionManager.addToBottom(action); }
    protected void addToTop(AbstractGameAction action) { AbstractDungeon.actionManager.addToTop(action); }

    @Override
    public int compareTo(AbstractBuff buff) {
        return 0;
    }

    public void atPreBattle() {
    }

    public enum BuffType {
        BUFF, DEBUFF;
    }
}

