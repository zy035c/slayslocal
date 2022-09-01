package cards.red;

import actions.AbstractGameAction;
import actions.common.DamageAction;
import cards.AbstractCard;
import cards.DamageInfo;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;
import localization.CardStrings;

public class BodySlam extends AbstractCard {

    private static final CardStrings cardStrings = CardStrings.bodySlam();
    public static final String ID = "cards.red.Bash";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.description;
    public static final String IMG_PATH = "";
    public static final int ATTACK_DMG = 0;
    public static final int COST = 1;
    public static final int UPGRADE_COST = 0;

    public BodySlam() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, CardColor.RED,
                CardRarity.COMMON, CardTarget.SINGLE);
        this.baseDamage = ATTACK_DMG;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
    }

    @Override
    public void calculateDamage(AbstractPlayer player) {
        super.calculateDamage(player);
        this.damage += player.block;
    }

    @Override
    public String getDescriptionInBattle(AbstractPlayer player) {
        String descript = super.getDescriptionInBattle(player);
        descript = descript + "\n(Deal !DAMAGE! damage.)";
        descript = descript.replace(
                "!DAMAGE!",
                Integer.toString(this.damage)
        );
        return descript;
    }

    @Override
    public String getDescriptionOnTarget(AbstractPlayer player, AbstractCreature target) {
        calculateDamageOnTarget(player, target);
        String descript = getDescriptionInBattle(player);
        descript = descript.replaceAll(
                "\\d+",
                Integer.toString(this.damageOnTarget)
        );
        return descript;
    }

    @Override
    public void use(AbstractPlayer user, AbstractCreature target) {
        AbstractGameAction damageAct = new DamageAction(
                target,
                new DamageInfo(user, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT
        );
        AbstractDungeon.actionManager.addToTop(damageAct);
    }

    @Override
    public AbstractCard makeCopy() {
        return null;
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeBaseCost(UPGRADE_COST);
    }
}
