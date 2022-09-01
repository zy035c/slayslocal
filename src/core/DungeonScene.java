package core;

import cards.AbstractCard;
import core.AbstractCreature;
import dungeons.AbstractDungeon;
import dungeons.Exordium;
import ui.*;
import java.awt.*;
import java.util.ArrayList;
import ui.listeners.EndTurnListener;
import ui.window.CustomFrame;
import javax.swing.*;
import static ui.GUI.calcX;
import static ui.GUI.calcY;


public class DungeonScene implements Runnable {

    public Exordium dungeon;
    public CustomFrame frame;
    public GUI gui;

    public DungeonScene(GUI gui, Exordium dungeon, CustomFrame frame) {
        this.dungeon = dungeon;
        this.frame = frame;
        this.gui = gui;
        // SceneMouseListener mlistener = new SceneMouseListener(this.labels);
        // this.frame.addMouseListener(mlistener); // 两个方法call必须传入同一个lister的实例！
        // this.frame.addMouseMotionListener(mlistener);
        this.gui.endTurnButton.addActionListener(new EndTurnListener(this));
        init();
        start();
    }

    private static final int MAIN_X = calcX(550); // 550 + 170 = 720 = (1/2)1440
    private static final int MAIN_Y = calcY(180);
    private static final int MAIN_WIDTH = calcX(340);
    private static final int MAIN_HEIGHT = calcY(200);

    /******************************************************************************
     * 游戏主线程
     * init方法：仅在battle开始时call一次
     ******************************************************************************/
    public void init() {
        initCreatureUI();
        dungeon.init_dungeon();

        frame.repaint();
        try {
            gui.setMainCaption("Initiating Game...");
            Thread.sleep(700);
            gui.setMainCaption("Initiating Game..");
            Thread.sleep(600);
            gui.setMainCaption("Initiating Game.");
            Thread.sleep(500);
            gui.setMainCaption("");
        } catch (InterruptedException ignored) {}
        try {
            gui.setMainCaption("Battle Start");
            Thread.sleep(900);
            gui.setMainCaption("");
            Thread.sleep(180);
        } catch (InterruptedException ignored) {}

    }

    // 将在按钮内部的线程（另一个线程）中执行
    public void endTurn() {
        gui.setMainCaption(AbstractDungeon.onStagePlayer.name + " End Turn");
        dungeon.end_turn();

        frame.repaint();
        frame.revalidate();
        try {
            Thread.sleep(400);
            updateDungeonDisplay();
            updateCardDisplay(true, true); // 清空手牌、清空能量、更新buff，然后重新显示
            Thread.sleep(600);
        } catch (InterruptedException ex) { ex.printStackTrace(); }
        gui.clearMainCaption();

        start(); // 完成结束回合，开始新的回合：重新开始这个类的线程：回合线程
    }

    // [开始]一个回合：回合内的代码块
    @Override
    public void run() {
        dungeon.start_turn();
        resetPiles();

        frame.repaint();
        try {
            gui.setMainCaption(
                    AbstractDungeon.onStagePlayer.name + " Turn",
                    AbstractDungeon.onStagePlayer.turn_num
            );
            Thread.sleep(400);
            updateDungeonDisplay();
            updateCardDisplay(true, true);
            Thread.sleep(600);
            gui.clearMainCaption();
        } catch (InterruptedException ignored) {}
        frame.repaint();
    }

    /******************************************************************************
     * 线程相关
     ******************************************************************************/

    private boolean running;
    public Thread thread;
    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
            thread.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Previous thread: stop");
    }


    private final int[] GuiSetLayoutX = {calcX(80), calcX(1100)};
    private final int[] GuiSetLayoutY = {calcY(200), calcY(200)};

    public ArrayList<CreatureGUI> GuiSets = new ArrayList<>();
    /******************************************************************************
     * ---!!!额外gui!!!---
     * 在这里定义角色的加载位置
     * 比如说，有两个角色时摆放在屏幕的左和右
     * 有三个时把第三个摆放在上方，第四个在下方
     * 一种构想，还没有写完，目前只允许两个角色
     ******************************************************************************/
    public void initCreatureUI() {
        // 初始化每个creature的UI套装
        int i = 0;
        for (AbstractCreature c : AbstractDungeon.getCreatures()) {
            if (i > 1) {
                break;  // only 2 players so far
            }
            this.GuiSets.add(new CreatureGUI(this.frame, GuiSetLayoutX[i], GuiSetLayoutY[i], c));
            i++;
        }
    }

    public boolean onSomeCreature(CardPane label) {
        for (CreatureGUI cgui: GuiSets) {
            if (cgui.cardOnCreature(label)) {
                return true;
            }
        }
        return false;
    }

    /******************************************************************************
     *  关于牌的一些方法。
     *
     ******************************************************************************/

    public ArrayList<CardPane> panes = new ArrayList<>(); // 装目前场上显示的所有手牌的CustomLabel

    private final int CARD_Y = calcY(670);
    private final int CARD_X_START = calcX(200);
    private final int CARD_X_END = calcX(1240); // full length 1440
    private final int CARD_X_RANGE = CARD_X_END - CARD_X_START;
    private final int CARD_WIDTH = calcX(180);

    public void clearCardDisplay() {
        for (CardPane pane : this.panes) {
            pane.setVisible(false);
            pane.setEnabled(false);
        }
        this.panes.clear();
    }

    public void updateCardDisplay(boolean inBattle, boolean inTurn) {
        clearCardDisplay();
        if (inBattle && inTurn) {
            int num = AbstractDungeon.onStagePlayer.hand.size(); // 获取当前场上player的手牌数量
            System.out.println("Updating screen card panes: " + num);
            int[] x_corr = new int[num];
            for (int i = 0; i < num; i++) {
                x_corr[i] = CARD_X_START + ((CARD_X_RANGE / (num + 1)) * (i + 1)) - (CARD_WIDTH / 2);
            }

            int i = 0;
            for (AbstractCard card : AbstractDungeon.onStagePlayer.hand.deckCards) {
                CardPane pane = new CardPane(
                        card, x_corr[i], CARD_Y, this
                );
                pane.updateDescription(AbstractDungeon.onStagePlayer);
                // 回合内、战斗内更新卡牌外观：
                // 传入打牌的人
                this.panes.add(pane);
                this.frame.addComp(pane, JLayeredPane.DRAG_LAYER);
                i++;
            }
            System.out.println("Updating screen card panes done.");
        }
        frame.repaint();
    }

    @Deprecated
    public void initGUI() {
        if (AbstractDungeon.onStagePlayer == null) {
            return;
        }
        int e = AbstractDungeon.onStagePlayer.energy;
        int eCap = AbstractDungeon.onStagePlayer.energyCap;
        gui.updateEnergyPanel(e, eCap);
        gui.updateDiscardNumber(AbstractDungeon.onStagePlayer.discardPile.size());
        gui.updateDrawNumber(AbstractDungeon.onStagePlayer.drawPile.size());
        for (CreatureGUI cgui: GuiSets) {
            cgui.updateBlockBar();
            cgui.updateHealthBar();
            cgui.updateBuffBar();
            gui.updatePlayerPanel(AbstractDungeon.onStagePlayer.name);
            gui.exhaust_pile.setText(Integer.toString(AbstractDungeon.onStagePlayer.exhaustPile.size()));
        }
    }

    /******************************************************************************
     *  没有动画前的替代方案。
     *  这个方法会更新如下内容：能量面板，烧牌堆、摸牌堆、弃牌堆、当前玩家名字
     *  creature GUI：生命、格挡、buff、戒指
     ******************************************************************************/
    public void updateDungeonDisplay() {

        if (AbstractDungeon.onStagePlayer == null) {
            return;
        }

        int e = AbstractDungeon.onStagePlayer.energy;
        int eCap = AbstractDungeon.onStagePlayer.energyCap;
        gui.updateEnergyPanel(e, eCap);

        gui.updateDiscardNumber(AbstractDungeon.onStagePlayer.discardPile.size());
        gui.updateDrawNumber(AbstractDungeon.onStagePlayer.drawPile.size());
        gui.updateExhaustNumber(AbstractDungeon.onStagePlayer.exhaustPile.size());
        gui.updatePlayerPanel(AbstractDungeon.onStagePlayer.name);

        for (CreatureGUI cgui: GuiSets) {
            cgui.updateBlockBar();
            cgui.updateHealthBar();
            cgui.updateBuffBar();
            cgui.updateRingBar();
        }
    }

    public void resetPiles() {
        if (gui == null) {
            return;
        }
        gui.setPiles(
                AbstractDungeon.onStagePlayer.drawPile,
                AbstractDungeon.onStagePlayer.discardPile,
                AbstractDungeon.onStagePlayer.exhaustPile,
                AbstractDungeon.onStagePlayer.masterDeck
        );
    }

}
