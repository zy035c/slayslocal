import core.AbstractCreature;
import dungeons.CustomFrame;
import dungeons.Exordium;
import core.Player;
import core.GUI;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

class Game {

    GUI gui;
    Exordium dungeon;

    public Game() {
        ArrayList<AbstractCreature> p_list = new ArrayList<>();
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        p_list.add(p1);
        p_list.add(p2);
        dungeon = new Exordium(p_list);

        CustomFrame frame = new CustomFrame();
        gui = new GUI(frame, dungeon);


    }

//    @Override
//    public void init() throws Exception {
//
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//
//    }
//
//    @Override
//    public void stop() throws Exception {
//
//    }

    public static void main(String[] args) {

        Game game = new Game();
        game.dungeon.init_dungeon();
        game.gui.updateDungeonDisplay();

        game.dungeon.start_turn();

        game.gui.updateCardDisplay();
        game.gui.endTurn = false;
        // game.gui.updateCardDisplay();
        game.gui.updateDungeonDisplay();

        while (true) {
            if (game.dungeon.need_update) {
                game.gui.updateDungeonDisplay();
                game.gui.updateCardDisplay();
                game.dungeon.need_update = false;
            }
        }
//        while (true) {
//            game.gui.updateDungeonDisplay();
//        }


            // 小循环，直到交换回合

//            do {
//
//                if (game.dungeon.need_update) {
//                    System.out.println("happening");
//                    game.gui.updateCardDisplay();
//                    game.gui.updateDungeonDisplay();
//                }
//
//            } while (!game.gui.endTurn);
//            game.dungeon.end_turn();

    }

}