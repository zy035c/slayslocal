package cards.colorless;

import actions.AbstractGameAction;
import actions.unique.ApotheosisAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

import static localization.CardStrings.getCardStrings;

public class Apotheosis extends AbstractCard {

    public static final String ID = "cards.colorless.Apotheosis";
    public static final String NAME = "Apotheosis";
    private static final CardStrings cardStrings = getCardStrings("Apotheosis");
    public static final String DESCRIPTION = "Upgrade ALL of your cards for the rest of combat." +
            "\nExhaust.";
    public static final String IMG_PATH = "";

    private static final int COST = 2;

    DamageInfo.DamageType damageTypeForTurn;

    public Apotheosis() {
        super("Apotheosis", NAME, "",
                COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                CardColor.COLORLESS, AbstractCard.CardRarity.RARE,
                AbstractCard.CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer player, AbstractCreature m) {
        calculateMagicNumber(player);
        AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ApotheosisAction());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy() {
        return new Apotheosis();
    }
}