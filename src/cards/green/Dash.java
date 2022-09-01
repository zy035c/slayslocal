package cards.green;

import actions.AbstractGameAction;
import actions.common.DamageAction;
import actions.common.GainBlockAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class Dash extends AbstractCard {

    public static final String ID = "cards.green.Dash";
    public static final String NAME = "Dash";
    public static final String DESCRIPTION = "Gain !BLOCK! Block.\n" +
            "Deal !DAMAGE! damage.";
    public static final String IMG_PATH = "";

    private static final int COST = 2;
    private static final int BASE_BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int BASE_DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Dash() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, CardColor.GREEN,
                CardRarity.UNCOMMON, CardTarget.SINGLE);
        this.baseBlock = BASE_BLOCK;
        this.baseDamage = BASE_DAMAGE;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractCreature m) {
//        calculateBlock();
//        calculateDamage();
        AbstractGameAction action = new GainBlockAction(p, p, this.block);
        AbstractDungeon.actionManager.addToTop(action);
        DamageInfo info = new DamageInfo(p, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, info));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dash();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
