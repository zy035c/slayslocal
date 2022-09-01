package ui.listeners;

import dungeons.AbstractDungeon;
import core.DungeonScene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class EndTurnListener implements ActionListener {

    DungeonScene scene;

    public EndTurnListener(DungeonScene scene) {
        this.scene = scene;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scene.stop();
        Thread endBtnThread = new Thread(scene) {
            @Override
            public void run() {
                scene.endTurn();
                // stop(); // 会自动销毁/终止线程？
            }
        };
        endBtnThread.start();
    }
}
