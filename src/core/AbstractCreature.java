package core;

import cards.DamageInfo;

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


    public abstract void damage(DamageInfo paramDamageInfo);

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

    // 还没有implement buffs
    public boolean hasBuff(String buffName) {
       return false;
    }
}
