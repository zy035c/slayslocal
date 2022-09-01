package cards.red;

import buffs.AbstractBuff;
import actions.AbstractGameAction;
import actions.common.ApplyBuffAction;
import actions.common.DamageAction;
import buffs.CompromiseBuff;

import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class Bash extends AbstractCard {
    // private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Bash");
    public static final String ID = "cards.red.Bash";
    public static final String NAME = "Bash";
    public static final String DESCRIPTION = "Deal !DAMAGE! damage.\n" +
            "Apply !MAGIC! Compromise.";
    public static final String IMG_PATH = "";

    private static final int COST = 2;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_MGC = 1;
    public Bash() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SINGLE);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = MAGIC_NUMBER;
        // this.magicNumber = this.baseMagicNumber;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
        calculateDamage(p);
        // if SETTING.debug
        // addToBot(new DamageAllEnemiesAction(
        // else {
        AbstractGameAction act1 = new DamageAction(
                m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        );
        AbstractDungeon.actionManager.addToTop(act1);
        AbstractBuff buff = new CompromiseBuff(m, this.magicNumber, false);
        AbstractGameAction act2 = new ApplyBuffAction(m, p, buff, this.magicNumber);
        AbstractDungeon.actionManager.addToTop(act2);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MGC);
        }
    }

    public AbstractCard makeCopy() {
        return new Bash();
    }
}
