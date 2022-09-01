package buffs;

import core.AbstractPlayer;

public class NoDrawBuff extends AbstractBuff {

    private static final String ID = "NoDraw";
    private static final String NAME = "No Draw";
    private static String IMA_PATH = "";
    private static String description = "You cannot draw cards this<br>turn anymore.";

    public NoDrawBuff(AbstractPlayer p, int magicNumber, boolean b) {
        super(ID, NAME,IMA_PATH, p, magicNumber,
                description, false, false);
        stackable = false; // 不能叠加层数
        cancellable = false;
        loseAllAtEndTurn = b;
    }


}
