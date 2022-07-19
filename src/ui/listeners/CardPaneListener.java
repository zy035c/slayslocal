package ui.listeners;

import actions.GameActionManager;
import cards.AbstractCard;
import core.AbstractCreature;
import dungeons.AbstractDungeon;
import dungeons.DungeonScene;
import ui.CardPane;
import ui.CreatureGUI;
import ui.CustomLabel;
import ui.GameColors;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPaneListener extends MouseAdapter {

    CardPane pane;
    DungeonScene scene;
    public CardPaneListener(CardPane pane, DungeonScene scene) {
        this.pane = pane;
        this.scene = scene;
    }

    private int dragOffsetX;
    private int dragOffsetY;

    @Override
    public void mouseDragged(MouseEvent evt) {
        // System.out.println("dragging");
        pane.moveTo(
                pane.getX() + evt.getX() - 50,
                pane.getY() + evt.getY() - 50
        );
//        if (this.scene.onSomeCreature(pane) || pane.onSelfPlayArea()) {
//            pane.setBackground(GameColors.green2);
//        } else {
//            pane.setBackground(GameColors.green1);
//        }
    }

    // 松开鼠标时判断
    @Override
    public void mouseReleased(MouseEvent e) {
        // 根据牌的作用类型（只能对自己，对一个敌人，对所有敌人...）来选择目标
        // 因为场上只有两个玩家所以直接选择敌人，除非牌的作用类型是SELF
        // 不同作用类型决定了这张卡应该被拖到什么地方才能生效
        if (pane.card.targetType == AbstractCard.CardTarget.SELF ||
                pane.card.targetType == AbstractCard.CardTarget.NONE) {
            if (pane.onSelfPlayArea()) {
                tryPlayCard(AbstractDungeon.onStagePlayer);
                return;
            }
        }

        if (pane.card.targetType == AbstractCard.CardTarget.SINGLE) {
            for (CreatureGUI cgui: scene.GuiSets) {
                if (cgui.cardOnCreature(pane) && cgui.getCreature() != AbstractDungeon.onStagePlayer) {
                    tryPlayCard(cgui.getCreature());
                    return;
                }
            }
        }

        pane.moveBack();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pane.updateDisplay();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // pane.setBackground(GameColors.grey1);
    }

    public void tryPlayCard(AbstractCreature target) {
        System.out.println("...try play card");
        if (pane.card.canUse(AbstractDungeon.onStagePlayer, target)) {
            // 如果不能canUse那么也无法打出
            // shoule be target selected
            System.out.println("-> " + AbstractDungeon.onStagePlayer.name + " played "
                    + pane.card.NAME + ". Target: " + target.name);

            // remove label from screen
            pane.destroy();

            // gui.labels.remove(myself); // remove this CustomLabel from GUI's labels
            // 采用新办法：update时清空所有卡label，重新从hand中读取创建
            // 因此不需要remove了！

            AbstractDungeon.onStagePlayer.useCard(pane.card, target, pane.card.costForTurn);

            // 等到动作队列全部结束后再update...
            while (true) {
                if (AbstractDungeon.actionManager.phase == GameActionManager.Phase.WAITING_ON_USER) {
                    break;
                }
            }
            scene.updateDungeonDisplay();
            scene.updateCardDisplay();

            return;
        } else {
            System.out.println("-> Cannot play " + pane.card.NAME + ".");
            pane.moveBack();
            return;
        }
    }
}
