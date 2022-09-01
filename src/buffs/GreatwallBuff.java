package buffs;

import core.AbstractCreature;

public class GreatwallBuff extends AbstractBuff {

    public static final String ID = "buffs.Greatwall";
    private static final String NAME = "Greatwall";
    private static final String description = "";
    private static String IMA_PATH = "";

    public GreatwallBuff(AbstractCreature m) {
        super(ID, NAME, IMA_PATH, m,
                1, description,
                false, false);
    }
}
