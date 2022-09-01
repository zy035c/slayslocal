package core;
import java.util.ArrayList;

import actions.AbstractGameAction;
import actions.common.*;
import buffs.AbstractBuff;
import cards.AbstractCard;
import cards.DamageInfo;
import cards.common.*;
import cards.red.*;
import actions.GameActionManager;
import cards.Deck;
import cards.red.Clash;
import dungeons.AbstractDungeon;
import rings.AbstractRing;

/******************************************************************************
 *  所有游戏玩家角色class的抽象父类，必须extend这个类。
 *
 *
 ******************************************************************************/

public abstract class AbstractPlayer extends AbstractCreature {

    public int energy = 0;
    public int energyCap;
    public int startingMaxHP; // 游戏里开局的HP上限

    public Deck drawPile; // 抽牌堆
    public Deck discardPile; // 弃牌堆
    public Deck hand; //手牌组
    public Deck masterDeck; // 卡组
    public Deck exhaustPile;
    public Deck limbo;

    public int hand_max; // 手牌上限
    public int drawNumber; // 摸牌数量


    // RPG abilities
    int agile;
    int strength;
    int intelligence;

    public abstract ArrayList<String> getStartingDeck();
    // public abstract ArrayList<String> getStarterDeck();

    public AbstractPlayer(String name, int maxHP, int hand_max, int drawNumber) {
        this.name = name;
        this.isPlayer = true;
        this.maxHP = maxHP;
        this.startingMaxHP = maxHP;
        this.hand_max = hand_max;
        this.drawNumber = drawNumber;
        // this.gameHandSize = 10;

        drawPile = new Deck(Deck.DECK_TYPE.DRAW_PILE);
        discardPile = new Deck(Deck.DECK_TYPE.DISCARD_PILE);
        hand = new Deck(Deck.DECK_TYPE.HAND);
        masterDeck = new Deck(Deck.DECK_TYPE.MASTER_DECK);
        exhaustPile = new Deck(Deck.DECK_TYPE.EXHAUST_PILE);
        limbo = new Deck(Deck.DECK_TYPE.LIMBO);
    }

    protected void initializeClass() {
//        exhaustPile = new Deck(Deck.DECK_TYPE.EXHAUST_PILE);

    }

    /******************************************************************************
     *  测试各种功能的方法集合在下面。
     *
     ******************************************************************************/

    // this is an auto init of a deck
    // used in testing demo.
    public void initializeTestDeck() {
        // num = 13
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());

        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Clash());

        this.masterDeck.addToTop((AbstractCard)new Anger());
        this.masterDeck.addToTop((AbstractCard)new Anger());
    }

    public void initializeStarterDeck() {
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
    }

    /******************************************************************************
     *  关于回合的方法
     ******************************************************************************/

    public int turn_num = 0; // 第几回合

    public int drawNumberForTurn = -1;
    public int energyRechargeForTurn = -1;
    public void getDrawNumber() {
        // buff, ring...
        drawNumberForTurn = this.drawNumber; // 本回合摸牌数
    }

    public void getEnergyRechargeNumber() {
        energyRechargeForTurn = this.energyCap; // 本回合获得能量数
    }

    public void startTurn() {
        turn_num++;
        atTurnStart(); // activate buffs and rings
        getDrawNumber();
        getEnergyRechargeNumber();
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(this, drawNumberForTurn, false));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(energyRechargeForTurn)); // recharge energy
        this.loseBlock(); // reset block value
        AbstractDungeon.actionManager.emptyQueue();
    }


    public void endTurn() {
        atTurnEnd(); // activate buffs and rings
        AbstractDungeon.actionManager.addToTop(new DiscardAtEndOfTurnAction()); // discard all cards
        loseEnergyAtTurnEnd();
        AbstractDungeon.actionManager.emptyQueue();
    }

    public void loseEnergyAtTurnEnd() {
        this.energy = 0;
    }

    /******************************************************************************
     *  关于改变属性的方法
     ******************************************************************************/
    // 获得e点能量
    public void gainEnergy(int e) {
        this.energy += e;
        System.out.println(this.name+" gain "+e+" energy."
                +" Now "+this.energy+" energy.");
    }

    // 失去e点能量
    public void loseEnergy(int e) {
        this.energy -= e;
        System.out.println(this.name+" lose "+e+" energy."
                +" Now "+this.energy+" energy.");
    }

    public void heal(int healAmount) {
    }

    //
    //
    //

    // 接受伤害，暂时不override

    @Override
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
        for (AbstractRing ring: this.rings) {
            if (ring.modifyDamageReceive) {
                damageAmount = ring.atDamageReceive(info, damageAmount);
            }
        }
        return damageAmount;
    }

    @Override
    public void damage(DamageInfo info) {
        boolean hadBlock = true;
        if (this.block == 0) {
            hadBlock = false;
        }
        int damageAmount = calculateDamageReceive(info); // for relics and buffs to take effect
        damageAmount = decrementBlock(info, damageAmount); // decrementBlock来自父类

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


    /******************************************************************************
     *  以下是关于牌的一些方法
     ******************************************************************************/

    // 从摸牌堆摸牌然后放进手牌堆堆的行为。并且返回被摸的牌
    public ArrayList<AbstractCard> drawAndPeek(int amount) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        if (amount <= 0) {
            System.out.println("DrawAndPeek: no card is drawn.");
            return cards;
        }
        // 限制摸牌数量，总手牌数不得超过上限
        if (this.hand.size() + amount > hand_max) {
            // messagebox
            return drawAndPeek(hand_max - amount);
        }
        // 不可能发足够牌
        if (this.drawPile.size() + this.discardPile.size() < amount) {
            return drawAndPeek(this.drawPile.size() + this.discardPile.size());
        }
        // 摸牌堆没牌或者不够，洗牌
        if (this.drawPile.size() < amount || this.drawPile.size() <= 0) {
            AbstractDungeon.actionManager.addToBottom(new ShuffleAction(this));
            AbstractDungeon.actionManager.executeAction();
        }
        //
        for (int i = 0; i < amount; i++) {
            // System.out.println("before drawPile.drawFromTop size "+drawPile.size());
            AbstractCard card = drawPile.drawFromTop();
            cards.add(card);
            System.out.println(this.name + " draws a " + card.NAME);
            hand.addToTop(card);
            this.onCardDrawOrDiscard(); // 每摸一张牌，就触发一次？
            this.onRefreshHand();
        }
        // 如果摸完牌之后摸牌堆没牌，并不立即洗牌。下次需要摸牌时触发

        if (cards.size() <= 0) {
            System.out.println("DrawAndPeek: no card is drawn.");
        }
        return cards;
    }

    // 从摸牌堆摸牌然后放进手牌堆堆的行为。不返回被摸的牌
    public void draw(int amount) {
        drawAndPeek(amount);
    }

    // 多态，摸一张或者amount张
    public void draw() {
        draw(1);
    }
    // avoid outOfBoundException
    public ArrayList<AbstractCard> drawAndPeek() {
        return drawAndPeek(1);
    }

    public void useCard(AbstractCard c, AbstractCreature target, int energyOnUse) {

        if (c.type == AbstractCard.CardType.ATTACK) {
            // useFastAttackAnimation();
        }

//        c.calculateCardDamage(this, target); // 暂时没用，空方法

        // x-cost的卡：用光
//        if (c.cost == -1 && EnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
//            c.energyOnUse = EnergyPanel.totalCount;
//        }

        this.loseEnergy(energyOnUse);

        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }
        c.use(this, target);
        AbstractDungeon.actionManager.addToTop(new UseCardAction(c, target));

        this.hand.removeSpecificCard(c);
        this.onRefreshHand();

        // exhaust, go to draw pile...
        if (c.exhaust) {
            AbstractDungeon.actionManager.addToTop(new ExhaustAction(c));
        } else {
            AbstractGameAction act = new DiscardPlayAction(c);
            AbstractDungeon.actionManager.addToTop(act);
        }
        // this.onCardDrawOrDiscard();

        // this.onRefreshHand(); // 必须在队列已经空了再执行？不再需要了。
        AbstractDungeon.actionManager.emptyQueue();
    }

    // 把弃牌堆的牌shuffle并且作为新的摸牌堆
    public void reshuffle() {
        discardPile.shuffle();
        while (discardPile.size() > 0) {
            drawPile.addToTop(discardPile.drawFromTop());
        }
        System.out.println("Shuffle discard pile and put all into draw pile.");
    }

    // 把一张卡放在弃牌堆顶
    public void moveToDiscardPile(AbstractCard c) {
        discardPile.addToTop(c);
        this.onCardDrawOrDiscard();
    }

    // 把一张 特定的 卡从手牌丢到弃牌堆 "而不是打出卡"
    public void discardFromHand(AbstractCard c) {
        this.hand.removeSpecificCard(c);
        this.onRefreshHand();
        moveToDiscardPile(c); // 这个方法里含有this.onCardDrawOrDiscard()
    }

    // 烧掉这张牌（放入烧牌堆
    public void exhaustCard(AbstractCard c) {
        c.onExhaust();
        exhaustPile.addToTop(c);
    }

    /******************************************************************************
     *  以下是关于Ring和Buff的code
     ******************************************************************************/
    public ArrayList<AbstractRing> rings = new ArrayList<>();

    public void atPreBattle() {
        for (AbstractRing r: this.rings) {
            r.atPreBattle();
        }
        //
    }

    @Override
    public void atTurnEnd() {
        ArrayList<AbstractBuff> buffsToCheck = new ArrayList<>(this.buffs);
        for (AbstractBuff buff: buffsToCheck) {
            buff.atEndOfTurn();
        }
    }

    public void atTurnStart() {
        for (AbstractRing r: this.rings) {
            r.atTurnStart();
        }
        //
    }

    // buff相关的在上一层的abstract creature里

    public boolean hasRing(String ringName) {
        if (this.rings.isEmpty()) {
            return false;
        }
        return this.getRing(ringName) != null;
    }

    public void obtainRing(AbstractRing ring) {
        ring.onObtain();
        this.rings.add(ring);
    }

    public AbstractRing getRing(String ringName) {
        for (AbstractRing r: this.rings) {
            if (r.name.equals(ringName)) {
                return r;
            }
        }
        return null;
    }

    public void onRefreshHand() {
        for (AbstractRing r: this.rings) {
            r.onRefreshHand();
        }
        // buff
    }

    @Deprecated
    public void onCardDrawOrDiscard() {
        for (AbstractRing r: this.rings) {
            // r.onCardDraw();
            r.onDrawOrDiscard();
        }
        // buff
    }

    public void initializeTestRings() {}

}
