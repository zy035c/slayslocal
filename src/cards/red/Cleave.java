package cards.red;

import actions.AbstractGameAction;
import actions.common.DamageAllEnemiesAction;
import cards.AbstractCard;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

public class Cleave extends AbstractCard {

    public static final String ID = "cards.red.Cleave";
    private static final CardStrings cardStrings = CardStrings.cleave();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.description;
    public static final String IMG_PATH = "";
    public static final int COST = 1;
    public static final int BASE_DMG = 8;
    public static final int UPGRADE_PLUS_DMG = 3;

    public Cleave() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, CardColor.RED,
                CardRarity.COMMON, CardTarget.ALL);
        this.baseDamage = BASE_DMG;
    }

    @Override
    public void use(AbstractPlayer player, AbstractCreature target) {
        DamageAllEnemiesAction act = new DamageAllEnemiesAction(
                player, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        AbstractDungeon.actionManager.addToTop(act);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Cleave();
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}
