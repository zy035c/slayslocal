package cards.red;

import actions.AbstractGameAction;
import actions.common.ApplyBuffAction;
import buffs.AbstractBuff;
import buffs.StrengthBuff;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

public class Inflame extends AbstractCard {

    public static final String ID = "cards.red.Inflame";
    private static final CardStrings cardStrings = CardStrings.inflame();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.description;
    public static final String IMG_PATH = "";
    public static final int COST = 1;
    public static final int BASE_MAGIC = 2;
    public static final int UPGRADE_PLUS = 1;

    public Inflame() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, CardColor.RED,
                CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = BASE_MAGIC;
    }

    @Override
    public void use(AbstractPlayer user, AbstractCreature target) {
        AbstractBuff buffToApply = new StrengthBuff(user, this.magicNumber);
        AbstractGameAction applyBuffAction = new ApplyBuffAction(
                user,
                user,
                buffToApply,
                this.magicNumber
        );
        AbstractDungeon.actionManager.addToTop(applyBuffAction);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Inflame();
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(UPGRADE_PLUS);
    }
}
