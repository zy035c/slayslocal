package core;

import cards.AbstractCard;
import cards.colorless.Apotheosis;
import cards.common.*;
import cards.red.*;
import cards.green.*;

import java.util.ArrayList;
public class TestPlayer2 extends AbstractPlayer {

    // SILENCE
    // private EnergyOrbInterface energyOrb = (EnergyOrbInterface)new EnergyOrbRed();


    public TestPlayer2(String name_) {
        super(name_, 999, 10, 5);
        // 名字，最大生命值，手牌上限，每回合摸牌数
        this.health = 999;
        this.energyCap = 3; // 能量上限
        initializeTestDeck();
        // initializeTestRings();
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
        // num = 11
        this.masterDeck.addToTop((AbstractCard)new Strike());
        this.masterDeck.addToTop((AbstractCard)new Defend());
        this.masterDeck.addToTop((AbstractCard)new Clash());
        this.masterDeck.addToTop((AbstractCard)new Apotheosis());
        this.masterDeck.addToTop((AbstractCard)new Bash());

        this.masterDeck.addToTop((AbstractCard)new Bash());
        this.masterDeck.addToTop((AbstractCard)new Dash());
        this.masterDeck.addToTop((AbstractCard)new Dash());
        this.masterDeck.addToTop((AbstractCard)new EscapePlan());
        this.masterDeck.addToTop((AbstractCard)new EscapePlan());

        this.masterDeck.addToTop((AbstractCard)new Apotheosis());
        this.masterDeck.addToTop((AbstractCard)new BodySlam());
        this.masterDeck.addToTop((AbstractCard)new BodySlam());
        this.masterDeck.addToTop((AbstractCard)new Inflame());
        this.masterDeck.addToTop((AbstractCard)new Inflame());
    }

}

