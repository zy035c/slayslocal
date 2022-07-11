package actions.common;
import actions.AbstractGameAction;
// import AbstractGUI;
import core.AbstractCreature;
import dungeons.AbstractDungeon;
import core.AbstractPlayer;
import core.Player;

public class GainEnergyAction extends AbstractGameAction {
    private int energyGain;
    public GainEnergyAction(int amount) {
        setValues((AbstractCreature) AbstractDungeon.onStagePlayer, (AbstractCreature) AbstractDungeon.onStagePlayer, amount);
        this.energyGain = amount;
    }

    public void update() {
        AbstractDungeon.onStagePlayer.gainEnergy(this.energyGain);
        // AbstractGUI.updateEnergyPanel(AbstractDungeon.onStagePlayer);
    }
}
