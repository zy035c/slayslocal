package cards.common;
import actions.AbstractGameAction;
import cards.AbstractCard;
import core.AbstractCreature;
import actions.common.GainBlockAction;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class Defend extends AbstractCard{
    public static final String ID = "cards.common.Defend";
    public static final String NAME = "Defend";
    public static final String DESCRIPTION = "Gain !BLOCK! block.";
    public static final String IMG_PATH = "";

    private static final int COST = 1;
    private static final int BASE_BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    public Defend() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.baseBlock = BASE_BLOCK;
    }

    public void use(AbstractPlayer p) {
        // calculateBlock();
        AbstractGameAction action = new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block);
        AbstractDungeon.actionManager.addToTop(action);
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
        use(p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            System.out.println(this.NAME+" upgraded");
        }
    }
    public AbstractCard makeCopy() {
        return new Defend();
    }

}
