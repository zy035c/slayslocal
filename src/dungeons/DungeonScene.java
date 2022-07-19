package dungeons;

import cards.AbstractCard;
import core.AbstractCreature;
import ui.*;

import java.awt.event.*;
import java.util.ArrayList;

import ui.window.CustomFrame;

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
        this.gui.endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("-> Turn Button Clicked.");
                dungeon.end_turn();
                dungeon.start_turn();
                updateDungeonDisplay();
                updateCardDisplay();
            }
        });
        start();
        init();
    }

    /******************************************************************************
     * MouseListener相关
     ******************************************************************************/


    /******************************************************************************
     * 线程相关
     ******************************************************************************/

    public void init() {
        initCreatureUI();
        dungeon.init_dungeon();
        updateDungeonDisplay();
        gui.setPiles(
                AbstractDungeon.onStagePlayer.drawPile,
                AbstractDungeon.onStagePlayer.discardPile,
                AbstractDungeon.onStagePlayer.exhaustPile,
                AbstractDungeon.onStagePlayer.masterDeck
        );

        dungeon.start_turn();
        updateDungeonDisplay();
        updateCardDisplay();
    }

    private boolean running;
    private Thread thread;
    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        // repaint();
    }

    /******************************************************************************
     * 在这里定义角色的加载位置
     * 比如说，有两个角色时摆放在屏幕的左和右
     * 有三个时把第三个摆放在上方，第四个在下方
     * 一种构想，还没有写完，目前只允许两个角色
     ******************************************************************************/
    private final int[] GuiSetLayoutX = {calcX(80), calcX(1100)};
    private final int[] GuiSetLayoutY = {calcY(200), calcY(200)};

    public ArrayList<CreatureGUI> GuiSets = new ArrayList<>();
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

    public void updateCardDisplay() {
        clearCardDisplay();
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
            this.panes.add(pane);
            this.frame.add(pane);
            i++;
        }
        System.out.println("Updating screen card panes done.");
    }

    public void endTurn() {

    }

    // 没有动画前的替代方案
    public void updateDungeonDisplay() {

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
        }
        gui.updatePlayerPanel(AbstractDungeon.onStagePlayer.name);
        gui.exhaust_pile.setText(Integer.toString(AbstractDungeon.onStagePlayer.exhaustPile.size()));
    }

}
