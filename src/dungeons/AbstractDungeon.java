package dungeons;
import actions.common.DiscardAllHandAction;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import core.AbstractCreature;
import java.util.ArrayList;
import actions.GameActionManager;
import core.AbstractPlayer;
import core.Player;

/******************************************************************************
 *  所有dungeon（游戏场景）的抽象父类，所有GameAction都必须extend这个类，并且实现抽象方法。
 *  一个dungeon有属于自己的GameActionManager。
 *  onStagePlayer指向当前回合的玩家。
 *  creature_list装有所有游戏内的玩家和生物。
 *
 ******************************************************************************/

public abstract class AbstractDungeon {
    public static String name;
    public static String levelNum;
    public static String id;
    public static int floorNum = 0;
    public static int actNum = 0;

    public static GameActionManager actionManager;
    public static AbstractPlayer onStagePlayer;
    public static ArrayList<AbstractCreature> creature_list;

    public AbstractDungeon(String name, ArrayList<AbstractCreature> p_list, SaveFile saveFile) {
        creature_list = p_list;
        for (AbstractCreature creature: creature_list) {
            if (creature.isPlayer) {
                onStagePlayer = (AbstractPlayer) creature;
                break;
            }
        }
        if (onStagePlayer == null) {
            System.out.println("WARNING! NO onStagePlayer.");
        }

        AbstractDungeon.actionManager = new GameActionManager();
        AbstractDungeon.name = name;


    }

    // 返回一个含有所有敌人（除去当前onStagePlayer）的列表
    public static ArrayList<AbstractCreature> getEnemies() {
        if (creature_list.size() <= 1) {
            return null;
        }
        ArrayList<AbstractCreature> enemies = new ArrayList<>(creature_list);
        enemies.remove(onStagePlayer);
        return enemies;
    }


    // 查看是否所有敌人已经死亡
    public static boolean areEnemiesBasicallyDead() {
        if (getEnemies() == null) {
            return true;
        }
        for (AbstractCreature c: getEnemies()) {
            if (!c.isDead && !c.isDying) {
                return false;
            }
        }
        return true;
    }

    public abstract void init_dungeon();

    public static ArrayList<AbstractCreature> getCreatures() {
        return creature_list;
    }


}
