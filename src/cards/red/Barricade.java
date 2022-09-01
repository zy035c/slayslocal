package cards.red;

import actions.common.ApplyBuffAction;
import buffs.GreatwallBuff;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

public class Barricade extends AbstractCard {

    public static final String ID = "cards.red.Barricade";
    public static final String NAME = "Barricade";
    public static final String DESCRIPTION = CardStrings.barricade().description;
    public static final String IMG_PATH = "";
    public static final int UPGRADE_COST = 0;

    private static final int COST = 3;

    public Barricade() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer user, AbstractCreature target) {
        AbstractDungeon.actionManager.addToTop(new ApplyBuffAction(
                user,
                user,
                new GreatwallBuff(user)
        ));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Barricade();
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeBaseCost(UPGRADE_COST);
    }
}
