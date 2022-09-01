package cards.red;

import actions.AbstractGameAction;
import actions.common.ApplyBuffAction;
import actions.common.DamageAction;
import buffs.AbstractBuff;
import buffs.CompromiseBuff;
import buffs.PowerlessBuff;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

public class Clothesline extends AbstractCard {

    private static final CardStrings cardStrings = CardStrings.clothesline();
    public static final String ID = "cards.red.Clothesline";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.description;
    public static final String IMG_PATH = "";

    private static final int COST = 2;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_MGC = 1;

    public Clothesline() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SINGLE);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = MAGIC_NUMBER;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractCreature m) {
        AbstractGameAction act1 = new DamageAction(
                m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        );
        AbstractDungeon.actionManager.addToTop(act1);
        AbstractBuff buff = new PowerlessBuff(m, this.magicNumber, false);
        AbstractGameAction act2 = new ApplyBuffAction(m, p, buff, this.magicNumber);
        AbstractDungeon.actionManager.addToTop(act2);
    }

    @Override
    public AbstractCard makeCopy() {
        return null;
    }

    @Override
    public void upgrade() {

    }
}
