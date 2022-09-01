package core;

import actions.GameActionManager;
import buffs.AbstractBuff;
import cards.DamageInfo;
import java.util.ArrayList;

/******************************************************************************
 *  所有游戏生物、玩家、npc的抽象父类，必须extend这个类，并且实现damage()方法。
 *  所有生物都有HP，最大HP，格挡值。
 *
 ******************************************************************************/

public abstract class AbstractCreature {

    public int health;
    public int maxHP;
    public int block;
    public boolean isDying = false;
    public boolean isDead = false;
    public boolean isPlayer = false;
    public String name;


    public void addBlock(int amount) {
        // buffs and relics
        block += amount;
        if (this.block > 999) {
            this.block = 999;
        }
        System.out.println(this.name+" gained "+amount+" block. "+
                "Now "+this.block + " block.");
    }

    private void brokeBlock() {
        // effect
    }

    public int calculateDamageReceive(DamageInfo info) {
        int damageAmount = info.output;
        if (damageAmount < 0) {
            damageAmount = 0;
        }
        for (AbstractBuff buff: this.buffs) {
            if (buff.modifyDamageReceive) {
                damageAmount = buff.atDamageReceive(info, damageAmount);
            }
        }
        return damageAmount;
    }

    public void damage(DamageInfo info) {
        boolean hadBlock = true;
        if (this.block == 0) {
            hadBlock = false;
        }
        int damageAmount = calculateDamageReceive(info);
        damageAmount = decrementBlock(info, damageAmount); // decrementBlock来自父类

        // for relics and buffs to take affect

        GameActionManager.damageReceivedThisTurn += damageAmount;
        GameActionManager.damageReceivedThisCombat += damageAmount;

        this.health -= damageAmount;
        System.out.println(this.name+" takes "+damageAmount+" damage."+
                " Now HP "+this.health);

//        if (damageAmount > 0 && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
//            updateCardsOnDamage();
//            this.damagedThisCombat++;
//        }

        if (this.health < 0) {
            this.health = 0;
        } else if (this.health < this.maxHP / 2.0F) {
            // effect
        }
        // AbstractGUI.updateHealthBar(this);

        if (this.health < 1) {
            this.isDead = true;
            // AbstractGUI.deathScreen();
        }
    };

    // 格挡被减少的方法
    protected int decrementBlock(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.HP_LOSS && this.block > 0) {
            // CardCrawlGame call upper abstract class to show effect
            if (damageAmount > this.block) {
                damageAmount -= this.block;
                // effect
                loseBlock();
                brokeBlock();
            } else if (damageAmount == this.block) {
                damageAmount = 0;
                loseBlock();
                brokeBlock();
                // effect
            } else {
                // effect
                loseBlock(damageAmount);
                damageAmount = 0;
            }
        }
        return damageAmount;
    }


    //
    //
    // 失去格挡。多态
    public void loseBlock(int amount, boolean noAnimation) {
        //
        this.block -= amount;
        if (this.block < 0) {
            block = 0;
        }
        System.out.println(this.name+" lose "+amount+
                " block. "+"Now "+this.block+" block.");
    }
    public void loseBlock(int amount) {
        loseBlock(amount, false);
    }
    public void loseBlock() {
        loseBlock(this.block);
    }

    public boolean isDeadOrEscaped() {
        if (this.isDying || this.isDead) {
            return true;
        }
        return false;
    }

    /******************************************************************************
     *  以下是关于Buff的code
     ******************************************************************************/
    public ArrayList<AbstractBuff> buffs = new ArrayList<>();

    public boolean hasBuff(String buff_id) {
        return getBuff(buff_id) != null;
    }

    public AbstractBuff getBuff(String buff_id) {
        for (AbstractBuff buff: buffs) {
            if (buff.ID.equals(buff_id)) {
                return buff;
            }
        }
        return null;
    }

    public void removeBuff(AbstractBuff buff) {
        boolean success = buffs.remove(buff);
        if (!success) {
            System.out.println("WARNING: removeBuff fails. No buff found.");
        }
    }

    // 暂时写在这里。在自己的回合结束时，触发所有buff的结束回合
    public void atTurnEnd() {
        for (AbstractBuff buff: this.buffs) {
            buff.atEndOfTurn();
        }
    }

    public void atPreBattle() {
        for (AbstractBuff buff: this.buffs) {
            buff.atPreBattle();
        }
    }
}
