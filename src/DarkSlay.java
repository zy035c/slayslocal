import core.AbstractCreature;
import core.TestPlayer1;
import core.TestPlayer2;
import core.DungeonScene;
import dungeons.Exordium;
import ui.window.CustomFrame;
import ui.GUI;

import java.util.ArrayList;

public class DarkSlay {

    public static void main (String[] args) {

        ArrayList<AbstractCreature> p_list = new ArrayList<>();
        TestPlayer1 p1 = new TestPlayer1("test_player_1");
        TestPlayer2 p2 = new TestPlayer2("test_player_2");
        p_list.add(p1);
        p_list.add(p2);
        Exordium dungeon = new Exordium(p_list);
        CustomFrame frame = new CustomFrame();
        GUI gui = new GUI(frame);
        DungeonScene scene = new DungeonScene(gui, dungeon, frame);
    }

}
