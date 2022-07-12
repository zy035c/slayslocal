package cards;

import core.AbstractCreature;

/******************************************************************************
 *  用来传递伤害信息。包括来源、目标、类型、数量、属性。
 *
 ******************************************************************************/

public class DamageInfo {
    public AbstractCreature owner;
    public String name;
    public DamageType type;
    public int base; // 上海数量
    public int output; // 实际输出伤害
    public boolean isModified = false;

    // public cards.DamageInfo(core.AbstractCreature damageSource, int base, DamageType type)
    public DamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        this.owner = damageSource;
        this.base = base;
        this.output = this.base;
        this.type = type;
    }

    public enum DamageType {
        NORMAL, THORNS, HP_LOSS, FIRE; // infusion
    }
}