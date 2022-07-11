package core;

import cards.Deck;
import cards.common.*;
import core.AbstractCreature;

import java.util.ArrayList;
public class Player extends AbstractPlayer {

    // IRONCLAD
    // private EnergyOrbInterface energyOrb = (EnergyOrbInterface)new EnergyOrbRed();


    public Player(String name_) {
        super(name_, 100, 10, 5);
        // 名字，最大生命值，手牌上限，每回合摸牌数
        this.health = 100;
        this.energyCap = 3; // 能量上限
        initializeTestDeck();
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

}

