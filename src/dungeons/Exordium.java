package dungeons;

import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import actions.common.*;
import java.util.ArrayList;
import actions.GameActionManager;
import core.GUI;
import core.Player;

/******************************************************************************
 *  当前的dungeon（游戏场景），叫做Exordium。
 *
 ******************************************************************************/

public class Exordium extends AbstractDungeon {

    public static final String ID = "Exordium";
    // GameActionManager actionManager;
    // public Player onStagePlayer;
    public boolean need_update = false;
    public ArrayList<Player> player_list = new ArrayList<>();

    public Exordium(ArrayList<AbstractCreature> p_list) {
        super(p_list);
        for (AbstractCreature p: p_list) {
            this.player_list.add((Player) p);
        }
        //System.out.println("!need_update at constructor"+need_update);
    }

    public void init_dungeon() {
        System.out.println("Dungeon initialization starts.");
        onStagePlayer = (Player) player_list.get(0);
        actionManager.addToTop(new GainBlockAction(player_list.get(1), 15));
        onStagePlayer.drawPile = onStagePlayer.masterDeck.makecopy();
        onStagePlayer.drawPile.shuffle();
        player_list.get(1).drawPile = player_list.get(1).masterDeck.makecopy();
        player_list.get(1).drawPile.shuffle();
        System.out.println("Dungeon initialization complete. Draw pile size is "
                + onStagePlayer.drawPile.size());
    }

    public void start_turn() {
        System.out.println("Turn starts.");
        // deal cards
        actionManager.addToTop(new DiscardAllHandAction(onStagePlayer));
        actionManager.executeAction();
        actionManager.addToTop(new DrawCardAction(onStagePlayer, onStagePlayer.drawNumber, false));

        // recharge energy
        onStagePlayer.energy = 0;
        actionManager.addToTop(new GainEnergyAction(onStagePlayer.energyCap));

        actionManager.emptyQueue();
        System.out.println("Turn start complete.");
    }

    public void playCard(AbstractCard card, AbstractCreature target) {
        card.use(onStagePlayer, target);
        actionManager.phase = GameActionManager.Phase.EXECUTING_ACTIONS;
        // lose energy?
        onStagePlayer.loseEnergy(card.costForTurn);
        actionManager.addToTop(new DiscardPlayAction(card));
        this.need_update = true;
        System.out.println("!need_update at playcard"+need_update);
        actionManager.executeAction();

        actionManager.emptyQueue();
    }

    public void end_turn() {
        System.out.println("Turn ends.");
        // discard all cards
        actionManager.addToTop(new DiscardAllHandAction(onStagePlayer));
        actionManager.emptyQueue();
        actionManager.endTurn();
        onStagePlayer.loseBlock();

        // shift player
        onStagePlayer = (Player)getEnemies().get(getEnemies().size()-1);
        System.out.println("Turn end complete.");
    }

    public static void main (String[] args) {

        ArrayList<AbstractCreature> p_list = new ArrayList<>();
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        p_list.add(p1);
        p_list.add(p2);
        Exordium dungeon = new Exordium(p_list);
        CustomFrame frame = new CustomFrame();
        GUI gui = new GUI(frame, dungeon);

        dungeon.init_dungeon();
        gui.updateDungeonDisplay();

        dungeon.start_turn();
        dungeon.onStagePlayer.energy = 99;
        gui.updateDungeonDisplay();
        gui.updateCardDisplay();

    }


}