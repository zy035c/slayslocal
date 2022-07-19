package core;
import java.util.ArrayList;

import actions.AbstractGameAction;
import actions.common.DiscardPlayAction;
import actions.common.ExhaustAction;
import actions.common.ShuffleAction;
import actions.common.UseCardAction;
import cards.AbstractCard;
import cards.DamageInfo;
import cards.common.*;
import cards.red.*;
import actions.GameActionManager;
import cards.Deck;
import cards.red.Clash;
import dungeons.AbstractDungeon;
import rings.AbstractRing;
import rings.Jormungandr;

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

    public void initializeTestRings() {
        this.rings.add(new Jormungandr());
    }

    /******************************************************************************
     *  测试方法们结束。
     *
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

    // 接受伤害
    public void damage(DamageInfo info) {
        int damageAmount = info.output;
        boolean hadBlock = true;
        if (this.block == 0) {
            hadBlock = false;
        }
        if (damageAmount < 0) {
            damageAmount = 0;
        }

//      if (damageAmount > 1 && hasPower("IntangiblePlayer")) {
//          damageAmount = 1;
//      }
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
    }

    public void heal(int healAmount) {
    }

    //
    //
    //

    /******************************************************************************
     *  以下是关于牌的一些方法
     *
     ******************************************************************************/

    // 多态，摸一张或者amount张
    public void draw() {
        draw(1);
    }

    public void draw(int amount) {
        // 限制摸牌数量，总手牌数不得超过上限
        if (this.hand.size() + amount > hand_max) {
            draw(hand_max - amount);
            // messagebox
            return;
        }
        // 不可能发足够牌
        if (this.drawPile.size() + this.discardPile.size() < amount) {
            draw(this.drawPile.size() + this.discardPile.size());
            return;
        }
        // 摸牌堆没牌或者不够，洗牌
        if (this.drawPile.size() < amount || this.drawPile.size() <= 0) {
            AbstractDungeon.actionManager.addToBottom(new ShuffleAction(this));
            AbstractDungeon.actionManager.executeAction();
        }
        //
        for (int i = 0; i < amount; i++) {
            // System.out.println("before drawPile.drawFromTop size "+drawPile.size());
            hand.addToTop(drawPile.drawFromTop());
            this.onCardDrawOrDiscard(); // 每摸一张牌，就触发一次？
            this.onRefreshHand();
        }
        // 如果摸完牌之后摸牌堆没牌，洗牌
        if (this.drawPile.size() <= 0 && !this.discardPile.isEmpty()) {
            AbstractDungeon.actionManager.addToTop(new ShuffleAction(this));
        }
    }

    public void useCard(AbstractCard c, AbstractCreature target, int energyOnUse) {

        if (c.type == AbstractCard.CardType.ATTACK) {
            // useFastAttackAnimation();
        }

        c.calculateCardDamage(target); // 暂时没用，空方法
        // 搞不懂什么意思
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

    public void exhaustCard(AbstractCard c) {
        c.onExhaust();
        exhaustPile.addToTop(c);
    }

    /******************************************************************************
     *  以下是关于Ring和Buff的code
     *
     ******************************************************************************/
    public ArrayList<AbstractRing> rings = new ArrayList<>();

    public void atPreBattle() {
        for (AbstractRing r: this.rings) {
            r.atPreBattle();
        }
        //
    }

    public void atTurnStart() {
        for (AbstractRing r: this.rings) {
            r.atTurnStart();
        }
        //
    }

    public boolean hasRing(String ringName) {
        if (this.rings.isEmpty()) {
            return false;
        }
        if (this.getRing(ringName) == null) {
            return false;
        }
        return true;
    }

    public AbstractRing getRing(String ringName) {
        for (AbstractRing r: this.rings) {
            if (r.name.equals(ringName)) {
                return r;
            }
        }
        System.out.println("null");
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
}
