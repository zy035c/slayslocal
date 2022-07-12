package dungeons;

import cards.AbstractCard;
import core.*;
import actions.common.*;
import java.util.ArrayList;

import ui.GUI;

/******************************************************************************
 *  当前的dungeon（游戏场景），叫做Exordium。
 *
 ******************************************************************************/

public class Exordium extends AbstractDungeon {

    public static final String ID = "Exordium";
    // GameActionManager actionManager;
    // public Player onStagePlayer;
    public boolean need_update = false;
    public ArrayList<AbstractPlayer> player_list = new ArrayList<>();
    public static final String name = "Exordium";

    public Exordium(ArrayList<AbstractCreature> p_list) {
        super(name, p_list, new SaveFile());
        for (AbstractCreature p: p_list) {
            this.player_list.add((AbstractPlayer) p);
        }
        //System.out.println("!need_update at constructor"+need_update);
    }

    public void init_dungeon() {
        System.out.println("Dungeon initialization starts.");
        // 把第一个玩家作为现在场上的
        onStagePlayer = (AbstractPlayer) player_list.get(0);
        actionManager.addToTop(new GainBlockAction(player_list.get(1), 15));
        //
        onStagePlayer.drawPile = onStagePlayer.masterDeck.makeCopy();
        onStagePlayer.drawPile.shuffle();
        player_list.get(1).drawPile = player_list.get(1).masterDeck.makeCopy();
        player_list.get(1).drawPile.shuffle();

        System.out.println("Dungeon initialization complete. Draw pile size is "
                + onStagePlayer.drawPile.size());
    }

    public void start_turn() {
        System.out.println("Turn starts.");
        // deal cards
        actionManager.startTurn();
        actionManager.addToTop(new DiscardAllHandAction(onStagePlayer));
        // actionManager.executeAction();
        actionManager.addToTop(new DrawCardAction(onStagePlayer, onStagePlayer.drawNumber, false));

        // recharge energy
        onStagePlayer.energy = 0;
        actionManager.addToTop(new GainEnergyAction(onStagePlayer.energyCap));

        // if?
        onStagePlayer.loseBlock();

        // activate buffs and rings
        onStagePlayer.atTurnStart();

        actionManager.emptyQueue();
        System.out.println("Turn start complete.");
    }

    public void playCard(AbstractCard card, AbstractCreature target) {
        onStagePlayer.useCard(card, target, card.costForTurn);
        this.need_update = true;
        System.out.println("!need_update at playcard"+need_update);

    }

    public void end_turn() {
        System.out.println("Turn ends.");
        actionManager.endTurn();
        // discard all cards
        actionManager.addToTop(new DiscardAtEndOfTurnAction());
        actionManager.emptyQueue();

        // shift player
        onStagePlayer = (AbstractPlayer)getEnemies().get(getEnemies().size()-1);
        System.out.println("Turn end complete.");
    }

    public static void main (String[] args) {

        ArrayList<AbstractCreature> p_list = new ArrayList<>();
        TestPlayer1 p1 = new TestPlayer1("p1");
        TestPlayer2 p2 = new TestPlayer2("p2");
        p_list.add(p1);
        p_list.add(p2);
        Exordium dungeon = new Exordium(p_list);
        CustomFrame frame = new CustomFrame();
        GUI gui = new GUI(frame, dungeon);

        dungeon.init_dungeon();
        gui.updateDungeonDisplay();

        dungeon.start_turn();

        gui.updateDungeonDisplay();
        gui.updateCardDisplay();

    }


}