package core;
import java.util.ArrayList;

import cards.AbstractCard;
import cards.DamageInfo;
import cards.common.*;
import cards.red.*;
import actions.GameActionManager;
import cards.Deck;
import cards.red.Clash;

/******************************************************************************
 *  所有游戏玩家角色class的抽象父类，必须extend这个类。
 *
 *
 ******************************************************************************/

public abstract class AbstractPlayer extends AbstractCreature{

    public int energy = 0;
    public int energyCap;
    public int startingMaxHP; // 游戏里开局的HP上限
    public Deck drawPile; // 抽牌堆
    public Deck discardPile; // 弃牌堆
    public Deck hand; //手牌组
    public Deck masterDeck; // 卡组
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
        drawPile = new Deck();
        discardPile = new Deck();
        hand = new Deck();
        masterDeck = new Deck();
    }

    protected void initializeClass() {

    }

    // this is an auto init of a deck
    // used in testing demo.
    public void initializeTestDeck() {
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Anger());
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

    // 把弃牌堆的牌shuffle并且作为新的摸牌堆
    public void reshuffle() {
        discardPile.shuffle();
        while (discardPile.size() > 0) {
            drawPile.addToTop(discardPile.drawFromTop());
        }
        System.out.println("Shuffle discard pile and put all into draw pile.");
    }

    // 把一张卡丢到弃牌堆
    public void moveToDiscardPile(AbstractCard c) {
        discardPile.addToTop(c);
    }

    // 把一张卡从手牌丢到弃牌堆
    public void discardFromHand(AbstractCard c) {
        this.hand.deckCards.remove(c);
        moveToDiscardPile(c);
    }

    public void gainEnergy(int e) {
        this.energy += e;
        System.out.println(this.name+" gain "+e+" energy."
                +" Now "+this.energy+" energy.");
    }

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

    // 多态，摸一张或者amount张
    public void draw() {
        if (this.hand.size() == hand_max) {
            return;
        }
        draw(1);
    }
    public void draw(int amount) {
        for (int i = 0; i < amount; i++) {
            hand.addToTop(drawPile.drawFromTop());
        }
    }

}
