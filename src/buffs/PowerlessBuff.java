package buffs;

import cards.DamageInfo;
import core.AbstractCreature;

public class PowerlessBuff extends AbstractBuff {
    public static String ID = "buffs.Powerless";
    public static String NAME = "Powerless";
    private static final String description = "Increases Attack damage by !AMOUNT!.";
    private static String IMG_PATH = "";

    public PowerlessBuff(AbstractCreature m, int magicNumber, boolean b) {
        super(ID, NAME, IMG_PATH, m, magicNumber,
                description, true, false);
        this.type = BuffType.BUFF;
        if (this.stack >= 999) {
            this.stack = 999;
        }
        this.modifyDamageGive = true;
    }

    public PowerlessBuff(AbstractCreature owner, int stack) {
        this(owner, stack, false);
    }

    // 在别的地方触发的时候，如果伤害类型符合，增加伤害
    public int atDamageGive(int damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage + this.stack;
        }
        return damage;
    }
}
