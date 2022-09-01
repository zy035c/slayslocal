package cards.common;

import actions.AbstractGameAction;
import actions.common.DamageAction;
import cards.AbstractCard;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import core.AbstractCreature;
import cards.DamageInfo;

public class Strike extends AbstractCard {

    public static final String ID = "cards.common.Strike";
    public static final String NAME = "Strike";
    public static final String DESCRIPTION = "Deal !DAMAGE! damage.";
    public static final String IMG_PATH = "";

    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Strike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SINGLE);
        this.baseDamage = ATTACK_DMG;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
        // damageTypeForTurn?
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
//        calculateDamage();
        DamageInfo tempInfo = new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new DamageAction((AbstractCreature)m, tempInfo));
    }



    public boolean isStrike() {
        //是否是最基础攻击牌
        return true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Strike();
    }

}