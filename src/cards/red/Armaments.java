package cards.red;

import actions.AbstractGameAction;
import actions.common.GainBlockAction;
import actions.unique.ArmamentsAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class Armaments extends AbstractCard {

    public static final String ID = "cards.red.Armaments";
    public static final String NAME = "Armaments";
    public static final String DESCRIPTION = "Gain 5 Block.\n" +
            "Upgrade a card in your hand for the rest of combat.";
    public static final String IMG_PATH = "";

    private static final int COST = 1;
    private static final int BASE_BLOCK = 5;

    public Armaments() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.baseBlock = BASE_BLOCK;
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
        // calculateBlock();
        AbstractGameAction act = new GainBlockAction(p, p, this.block);
        AbstractDungeon.actionManager.addToTop(act);
        AbstractDungeon.actionManager.addToTop(new ArmamentsAction(this.upgraded));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
            // also upgrade description
        }
    }
    public AbstractCard makeCopy() {
        return new Armaments();
    }
}