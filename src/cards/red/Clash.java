package cards.red;

import actions.AbstractGameAction;
import actions.common.DamageAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class Clash extends AbstractCard {
    public static final String ID = "cards.red.Clash";
    public static final String NAME = "Clash";
    public static final String DESCRIPTION = "Deal 14 damage.";
    public static final String IMG_PATH = "";

    private static final int COST = 0;
    private static final int ATTACK_DMG = 14;
    private DamageInfo.DamageType damageTypeForTurn;

    public Clash() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.SINGLE);
        this.baseDamage = ATTACK_DMG;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
        if (m != null) {
            DamageInfo tempInfo = new DamageInfo((AbstractCreature) p, this.baseDamage, this.damageTypeForTurn);
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new DamageAction((AbstractCreature) m, tempInfo));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractCreature m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        for (AbstractCard c: p.hand.deckCards) {
            if (c.type != CardType.ATTACK) {
                canUse = false;
                // message
            }
        }
        return canUse;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    public AbstractCard makeCopy() {
        return new Clash();
    }

}
