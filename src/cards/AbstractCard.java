package cards;

import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

/******************************************************************************
 *  所有卡的抽象父类，所有卡都必须extend这个类，并且实现构造器和use()方法。
 *  卡被打出时，向GameActionManager动作队列里添加对应的动作。
 *
 ******************************************************************************/
public abstract class AbstractCard {

    public String ID;
    public String NAME;
    public String DESCRIPTION;
    public String IMG_PATH;

    public CardType type; // 卡的种类
    public CardColor color; // 卡的颜色
    public CardRarity rarity; // 稀有度
    public CardTarget targetType; // 目标类型
    public int costForTurn; // 这回合的实际cost
    public int cost;
    public boolean freeToPlayOnce = false;
    public boolean isInAutoplay = false;

    // 定义各种牌会用到的基础数值。如果没有成功的初始化会等于-1
    public int baseDamage = -1, baseBlock = -1,
            baseMagicNumber = -1, baseHeal = -1,
            baseDraw = -1, baseDiscard = -1;
    public int damage = -1, block = -1,
            magicNumber = -1, heal = -1,
            draw = -1, discard = -1;

    protected DamageInfo.DamageType damageType;
    public DamageInfo.DamageType damageTypeForTurn;

    public boolean inBottleFlame = false;
    public boolean inBottleLightning = false;
    public boolean inBottleTornado = false;


    public AbstractCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION,
                        CardType type, CardColor color,
                        CardRarity rarity, CardTarget targetType) {

        this.ID = ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.IMG_PATH = IMG_PATH;
        this.cost = COST;
        this.type = type;
        this.color = color;
        this.rarity = rarity;
        this.targetType = targetType;
        // this.damageType = dType;
        this.costForTurn = this.cost;

        calculateDamage();
        calculateBlock();
        calculateMagicNumber();
    }

    // 简单判断有无可能被打出
    public boolean canPlay() {
        for (AbstractCard c : AbstractDungeon.onStagePlayer.hand.deckCards) {
            if (!c.canPlayAnotherCard(this)) {
                return false;
            }
        }
        // 状态和诅咒牌无法被打出
        if (this.type == CardType.STATUS && this.costForTurn < -1 // Medical Kit?
        ) {
            return false;
        }
        if (this.type == CardType.CURSE && this.costForTurn < -1 // Med Kit
        ) {
            return false;
        }

        // power, relics

        return hasEnoughEnergy();
    }

    // 能否对目标对象使用
    public boolean canUse(AbstractPlayer p, AbstractCreature m) {
        if (canPlay() && hasEnoughEnergy()) {
            return true;
        }
        return false;
    }

    // 目标是否可选
    public boolean cardPlayable(AbstractCreature m) {
        if (((this.targetType == CardTarget.SINGLE ||
                this.targetType == CardTarget.S_AND_SINGLE) && m.isDying) ||
        AbstractDungeon.areEnemiesBasicallyDead()) {
            // this.cantUseMessage = null;
            return false;
        }
        return true;
    }

    // 能量是否足够
    public boolean hasEnoughEnergy() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            // this.cantUseMessage = TEXT[9];
            return false;
        }

        if (AbstractDungeon.onStagePlayer.energy >= this.costForTurn || freeToPlay() || this.isInAutoplay) {
            return true;
        }
        return false;
    }

    // 返回能量花费的String
    public String getCost() {
        if (this.cost == -1) {
            return "X";
        }
        if (freeToPlay()) {
            return "0";
        }
        return Integer.toString(this.costForTurn);
    }

    public boolean freeToPlay() {
        if (this.freeToPlayOnce) {
            return true;
        }
        // if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
         //   (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
          //  AbstractDungeon.player.hasPower("FreeAttackPower") && this.type == CardType.ATTACK) {
         //   return true;
         //   }
        return false;
    }

    public abstract void use(AbstractPlayer paramAbstractPlayer, AbstractCreature paramAbstractMonster);



    // 创造一个数据完全一致的复制品。UUID不一致。
    @Deprecated
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = makeCopy();

        for (int i=0; i<this.timesUpgraded; i++) {
            card.upgrade();
        }

        card.NAME = this.NAME;
        card.targetType = this.targetType;
        card.timesUpgraded = this.timesUpgraded;
        card.upgraded = this.upgraded;
        card.baseDamage = this.baseDamage;
        card.baseBlock = this.baseBlock;
        // card.baseMagicNumber = this.baseMagicNumber;
        card.cost = this.cost;
        card.costForTurn = this.costForTurn;
        // card.isCostModified = this.isCostModified;
        // card.inBottleLightning = this.inBottleLightning;
        // card.inBottleFlame = this.inBottleFlame;
        // card.inBottleTornado = this.inBottleTornado;
        // card.isSeen = this.isSeen;
        // card.isLocked = this.isLocked;
        // card.misc = this.misc;
        card.freeToPlayOnce = this.freeToPlayOnce;
        return card;
    }

    @Deprecated
    public AbstractCard makeSameInstanceOf() {
        AbstractCard card = makeCopy();
        // card.uuid = this.uuid;
        return card;
    }

    public void calculateCardDamage(AbstractCreature target) {
        //

    }

    /******************************************************************************
     *  以下是关于卡使用、打出、结束回合弃牌时的特殊情况。
     *
     ******************************************************************************/

    public boolean retain = false; // 回合结束后是否保留
    public boolean exhaust = false; // 打出后是否exhaust
    public boolean selfRetain = false; // 不清楚是什么

    /******************************************************************************
     *  以下是关于upgrade的方法。
     *  也关于数字结算
     ******************************************************************************/
    int timesUpgraded = 0;
    public boolean upgraded = false;
    boolean upgradedCost;
    boolean isCostModified;
    boolean upgradedBlock = false;
    boolean upgradedDamage = false;
    boolean upgradedMagicNumber = false;
    boolean isBlockModified;
    boolean isDamageModified;

    public void calculateBlock() {
        this.block = this.baseBlock;
    }

    public void calculateDamage() {
        this.damage = this.baseDamage;
    }

    public void calculateMagicNumber() {
        this.magicNumber = this.baseMagicNumber;
    }

    protected void upgradeMagicNumber(int amount) {
        this.baseMagicNumber += amount;
        this.upgradedMagicNumber = true;
    }

    protected void upgradeDamage(int amount) {
        this.baseDamage += amount;
        this.upgradedDamage = true;
    }

    protected void upgradeBlock(int amount) {
        this.baseBlock += amount;
        this.upgradedBlock = true;
    }

    protected void upgradeName() {
        this.timesUpgraded++;
        this.upgraded = true;
        this.NAME += "+";
        // initializeTitle();
    }

    protected void upgradeBaseCost(int newBaseCost) {
        int diff = this.costForTurn - this.cost;
        this.cost = newBaseCost;

        if (this.costForTurn > 0) {
            this.costForTurn = this.cost + diff;
        }
        if (this.costForTurn < 0) {
            this.costForTurn = 0;
        }
        this.upgradedCost = true;
    }

    public boolean canUpgrade() {
        if (this.type == CardType.CURSE) {
            return false;
        }
        if (this.type == CardType.STATUS) {
            return false;
        }
        if (this.upgraded) {
            return false;
        }
        return true;
    }

    @Deprecated
    // about magic number
    public void displayUpgrades() {
        if (this.upgradedCost) {
            this.isCostModified = true;
        }
        if (this.upgradedDamage) {
            this.damage = this.baseDamage;
            this.isDamageModified = true;
        }
        if (this.upgradedBlock) {
            this.block = this.baseBlock;
            this.isBlockModified = true;
        }
        /*  822 */
//        if (this.upgradedMagicNumber) {
//            /*  823 */
//            this.magicNumber = this.baseMagicNumber;
//            /*  824 */
//            this.isMagicNumberModified = true;
//            /*      */
//        }
    }

    // 直接获得descr
    public String getDescription() {

        calculateBlock();
        calculateDamage();
        calculateMagicNumber();

        String descript = this.DESCRIPTION.replace(
                "!DAMAGE!",
                Integer.toString(this.damage)
        );
        descript = descript.replace(
                "!BLOCK!",
                Integer.toString(this.block)
        );
        descript = descript.replace(
                "!MAGIC!",
                Integer.toString(this.magicNumber)
        );
        return descript;
    }

    // 在选择了目标的时候获得descr
    public String getDescriptionOnTarget(AbstractCreature target) {
        return this.DESCRIPTION;
    }

    /******************************************************************************
     *  结束。
     *  以下是抽象方法 和等待继承的非抽象方法。
     ******************************************************************************/
    public abstract AbstractCard makeCopy(); // 抽象的方法

    public abstract void upgrade();

    public void triggerOnEndOfPlayerTurn(){};

    public void triggerOnManualDiscard(){};

    public void modifyCostForCombat(int i) {
    }

    // 有手上这张牌的时候，能否打出
    // @param card 这张牌呢
    // 等待override
    public boolean canPlayAnotherCard(AbstractCard card) {
        return true;
    }

    public void onExhaust() {}

    /******************************************************************************
     *  以下是枚举类。属性。
     ******************************************************************************/
    public enum CardType {
        ATTACK, SKILL, POWER, CURSE, STATUS;

        @Override
        public String toString() {
            switch (this) {
                case ATTACK:
                    return "Attack";
                case SKILL:
                    return "Skill";
                case POWER:
                    return "Power";
                case CURSE:
                    return "Curse";
                case STATUS:
                    return "Status";
            }
            return "?Unknown";
        }
    }

    public enum CardColor {
        RED, BLUE, GREEN, COLORLESS;
    }

    public enum CardRarity {
        BASIC, SPECIAL, COMMON, UNCOMMON, RARE, CURSE;
    }

    public enum CardTarget {
        SELF, SINGLE, PLURAL, ALL, NONE, S_AND_SINGLE, S_AND_PLURAL;
        // ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY, ALL;
    }

}