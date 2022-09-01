package dungeons;

import cards.AbstractCard;
import core.*;
import actions.common.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/******************************************************************************
 *  当前的dungeon（游戏场景），叫做Exordium。
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

        if (player_list.isEmpty()) {
            System.out.println("Running test: no players");
            return;
        }

        for (AbstractCreature c: getCreatures()) {
            AbstractPlayer p;
            if (c.isPlayer) {
                p = (AbstractPlayer) c;
                p.drawPile = p.masterDeck.makeCopy();
                p.drawPile.shuffle();
            }
            c.atPreBattle(); // activate buffs and rings
        }

        System.out.println("Dungeon initialization complete.");
    }

    public void start_turn() {
        System.out.println("Turn starts.");
        // 获得回合玩家 或者切换回合玩家
        if (onStagePlayer == null) {
            for (Iterator<AbstractCreature> iterator = getCreatures().iterator(); iterator.hasNext();) {
                AbstractCreature p = iterator.next();
                if (p.isPlayer) {
                    onStagePlayer = (AbstractPlayer) p;
                    break;
                }
            }
            if (onStagePlayer == null) {
                System.out.println("WARNING: START TURN FAILS TO GET PLAYER on initialization.");
            }
        } else {
            for (Iterator<AbstractCreature> iterator = Objects.requireNonNull(getEnemies()).iterator(); iterator.hasNext();) {
                AbstractCreature p = iterator.next();
                if (p.isPlayer) {
                    onStagePlayer = (AbstractPlayer) p;
                    break;
                }
            }
            if (onStagePlayer == null) {
                System.out.println("WARNING: START TURN FAILS TO GET PLAYER FROM getEnemies().");
            }
        }
        System.out.println("Getting player success: " + onStagePlayer.name);
        actionManager.startTurn();
        onStagePlayer.startTurn();
        actionManager.emptyQueue(); // 确保队列已空
        System.out.println("Turn start complete.");
    }

    // 似乎没用上的方法
    public void playCard(AbstractCard card, AbstractCreature target) {
        onStagePlayer.useCard(card, target, card.costForTurn);
        this.need_update = true; // 这个变量不起作用
        // System.out.println("!need_update at playcard"+need_update);
    }

    public void end_turn() {
        System.out.println("Turn ends.");
        actionManager.endTurn();
        onStagePlayer.endTurn();
        actionManager.emptyQueue(); // 确保队列已空
        System.out.println("Turn end complete.");
    }

}