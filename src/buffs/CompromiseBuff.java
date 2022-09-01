package buffs;

import cards.DamageInfo;
import core.AbstractCreature;

public class CompromiseBuff extends AbstractBuff {

    public static final String ID = "buffs.Compromise";
    private static final String NAME = "Compromise";
    public float rate = 1.5f;
    // 之后再来重写关于纸蛙的rate改写问题
    private static final String description = "Receive 50% more damage<br>" +
            "from Attacks for !AMOUNT! turn.";
    private static String IMA_PATH = "";

    public CompromiseBuff(AbstractCreature m, int magicNumber) {
        this(m, magicNumber, false);
    }

    public CompromiseBuff(AbstractCreature m, int magicNumber, boolean b) {
        super(ID, NAME,IMA_PATH, m, magicNumber,
                description, true, true);

        this.cancellable = true;
        this.stackable = true;
        this.type = BuffType.DEBUFF;
        this.loseAtEndTurn = true; // 在自己的回合结束时，失去一层
        this.modifyDamageReceive = true;
    }

    @Override
    public int atDamageReceive(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            return (int)(damageAmount * rate);
        }
        return damageAmount;
    }
}
