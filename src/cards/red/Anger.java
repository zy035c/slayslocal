package cards.red;

import actions.AbstractGameAction;
import actions.common.DamageAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import actions.common.MakeTempCardInDiscardAction;

public class Anger extends AbstractCard {

    public static final String ID = "cards.red.Anger";
    public static final String NAME = "Anger";
    public static final String DESCRIPTION = "Deal 14 damage.";
    public static final String IMG_PATH = "";

    private static final int COST = 0;
    private static final int ATTACK_DMG = 6;
    private DamageInfo.DamageType damageTypeForTurn;
    public Anger() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK,
                CardColor.RED, CardRarity.COMMON, CardTarget.SINGLE);
        this.baseDamage = ATTACK_DMG;
    }

    public void use(AbstractPlayer p, AbstractCreature c) {
        AbstractGameAction action = new DamageAction(p, new DamageInfo(p, this.damage, this.damageTypeForTurn));
        AbstractDungeon.actionManager.addToTop(action);
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
    }

    public AbstractCard makeCopy() {
        return new Anger();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

}
