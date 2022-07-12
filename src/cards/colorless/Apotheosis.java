package cards.colorless;

import actions.AbstractGameAction;
import actions.common.DamageAction;
import actions.unique.ApotheosisAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

import static localization.CardStrings.getCardStrings;

public class Apotheosis extends AbstractCard {

    public static final String ID = "Apotheosis";
    public static final String NAME = "Apotheosis";
    private static final CardStrings cardStrings = getCardStrings("Apotheosis");
    public static final String DESCRIPTION = "Deal !D! damage.";
    public static final String IMG_PATH = "";

    private static final int COST = 2;
    private static final int ATTACK_DMG = 0;
    private static final int UPGRADE_PLUS_DMG = 0;
    DamageInfo.DamageType damageTypeForTurn;

    public Apotheosis() {
        /* 16 */
        super("Apotheosis", NAME, "",
                COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                CardColor.COLORLESS, AbstractCard.CardRarity.RARE,
                AbstractCard.CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
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