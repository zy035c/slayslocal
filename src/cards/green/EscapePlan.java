package cards.green;

import actions.AbstractGameAction;
import actions.common.DrawCardAction;
import actions.common.GainBlockAction;
import cards.AbstractCard;
import cards.common.Defend;
import core.AbstractCreature;
import core.AbstractPlayer;
import dungeons.AbstractDungeon;

public class EscapePlan extends AbstractCard {

    public static final String ID = "cards.green.EscapePlan";
    public static final String NAME = "Escape Plan";
    public static final String DESCRIPTION = "Draw 1 card.\n" +
            "If you draw a Skill,\ngain !BLOCK! Block.";
    public static final String IMG_PATH = "";

    private static final int COST = 0;
    private static final int BASE_BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    public EscapePlan() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, CardColor.GREEN,
                CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
        this.baseBlock = BASE_BLOCK;
    }

    public void use(AbstractPlayer p) {
        DrawCardAction act = new DrawCardAction(p, 1);
        AbstractDungeon.actionManager.addToBottom(act); // ?bottom or top, not sure
        AbstractDungeon.actionManager.executeAction(); // 插队结算一下吧
        while (true) {
            if (act.isDone) {
                break;
            }
        }
        try {
            if (act.drawnCards.size() <= 0) {
                return;
            }
            if (act.drawnCards.get(0).type == CardType.SKILL) {
                // calculateBlock();
                AbstractGameAction action = new GainBlockAction(p, p, this.block);
                AbstractDungeon.actionManager.addToTop(action);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void use(AbstractPlayer p, AbstractCreature m) {
        use(p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            System.out.println(NAME + " upgraded.");
        }
    }
    public AbstractCard makeCopy() {
        return new EscapePlan();
    }

}
