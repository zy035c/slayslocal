package localization;


/******************************************************************************
 * 这是为了本地化而写的类。
 * 所有各种语言的文本都将写在这里，或者以后从外部文件读。
 ******************************************************************************/
public class ToolTipStrings extends LanguageStrings {
    public String title;
    public String description;

    // 格挡相关的说明
    public static ToolTipStrings blockStrings() {
        ToolTipStrings toolTipStrings = new ToolTipStrings();
        toolTipStrings.title = "Block";
        toolTipStrings.description = "Until next turn, prevents<br>damage.";
        return toolTipStrings;
    }

    // 结束回合的说明
    public static ToolTipStrings endTurnStrings() {
        ToolTipStrings toolTipStrings = new ToolTipStrings();
        toolTipStrings.title = "";
        toolTipStrings.description =
                "Pressing this button will end<br>" +
                "your turn.<br><br>" +
                "you will discard your hand,<br>" +
                "enemies will take their turn,<br>" +
                "you will draw 5 cards, then it<br>" +
                "will be your turn again.<br><br>" +
                "If your draw pile is empty, the<br>" +
                "discard pile is shuffled into<br>" +
                "the draw pile.";
        return toolTipStrings;
    }

    // 能量的说明
    public static ToolTipStrings energyPanelStrings() {
        ToolTipStrings toolTipStrings = new ToolTipStrings();
        toolTipStrings.title = "Energy";
        toolTipStrings.description = "Your current energy count.<br>" +
                "Cards require energy to play.";
        return toolTipStrings;
    }


    // 摸牌堆的说明
    public static ToolTipStrings drawPileStrings() {
        ToolTipStrings toolTipStrings = new ToolTipStrings();
        toolTipStrings.title = "Block";
        toolTipStrings.description = "Until next turn, prevents<br>damage.";
        return toolTipStrings;
    }

    // 弃牌堆的说明
    public static ToolTipStrings discardPileStrings() {
        ToolTipStrings toolTipStrings = new ToolTipStrings();
        toolTipStrings.title = "Block";
        toolTipStrings.description = "Until next turn, prevents<br>damage.";
        return toolTipStrings;
    }
}
