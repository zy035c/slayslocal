package buffs;

import core.AbstractCreature;

public class PulpBuff extends AbstractBuff {

    private static final String ID = "Pulp"; // localization?
    private static final String NAME = "Pulp";
    private static final String IMG_PATH = "";
    private static String description = "";


    public PulpBuff(AbstractCreature m, int magicNumber, boolean b) {
        super(ID, NAME, IMG_PATH, m, magicNumber, description,
                true, true);
        this.stackable = true;
        this.cancellable = false;
    }

    public void onSpecificTrigger() {
        // 用一层pulp抵消了一次上buff
        // animation
        this.decrementStack(1);
    }
}
