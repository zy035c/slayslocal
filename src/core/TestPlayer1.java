package core;

import cards.AbstractCard;
import cards.Deck;
import cards.colorless.Apotheosis;
import cards.common.*;
import cards.red.*;
import cards.red.Anger;
import cards.red.Clash;
import core.AbstractCreature;

import java.util.ArrayList;
public class TestPlayer1 extends AbstractPlayer {

    // IRONCLAD
    // private EnergyOrbInterface energyOrb = (EnergyOrbInterface)new EnergyOrbRed();


    public TestPlayer1(String name_) {
        super(name_, 100, 10, 5);
        // 名字，最大生命值，手牌上限，每回合摸牌数
        this.health = 100;
        this.energyCap = 99; // 能量上限
        initializeTestDeck();
        initializeTestRings();
        this.energy = 0; // 暂时
    }


    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Strike");
        retVal.add("Strike");
        retVal.add("Strike");
        retVal.add("Strike");
        retVal.add("Clash");
        retVal.add("Defend");
        retVal.add("Defend");
        retVal.add("Defend");
        retVal.add("Defend");
        retVal.add("Defend");
        return retVal;
    }

    @Override
    public void initializeTestDeck() {
        // num = 13
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Clash());

        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Anger());
        this.masterDeck.addToTop((AbstractCard)new Anger());
        this.masterDeck.addToTop((AbstractCard)new Apotheosis());
        this.masterDeck.addToTop((AbstractCard)new Apotheosis());

        this.masterDeck.addToTop((AbstractCard)new Bash());
        this.masterDeck.addToTop((AbstractCard)new Bash());
    }

}

