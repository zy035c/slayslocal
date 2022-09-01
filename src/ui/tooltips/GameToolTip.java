package ui.tooltips;

import localization.ToolTipStrings;
import ui.GameColors;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

import static ui.GUI.calcX;
import static ui.GUI.calcY;

/******************************************************************************
 * 重载一个component的createToolTip，在其中返回getStyle(new JToolTip())。
 * 然后用getHTML()获取String，传入setToolTipText
 ******************************************************************************/
public class GameToolTip {

    private static final int TOOLTIP_WIDTH = calcX(200);
    private static final int TOOLTIP_HEIGHT = calcY(60);

    public static String getHTML(String title, String text) {
        String titleHTML = "<p style=\"color: " +
                GameColors.getHTMLColorString(GameColors.toolTipTitle) +
                "; font-size: 9px;\">" +
                title +
                "</p>";
        String fullHTML = "";
        if (title.equals("")) { // 如果没有标题，不加入标题的html
            fullHTML = "<html>" +
                    "<div align=\"left\" style=\"padding:2px\">" + // Kreon?
                    "<p align=\"left\" style=\"font-size: 8.5px\">" +
                    text +
                    "</p></html></div>";
        } else {
            fullHTML = "<html>" +
                    "<div align=\"left\" style=\"padding:2px\">" + // Kreon?
                    titleHTML +
                    "<p align=\"left\" style=\"font-size: 8.5px\">" +
                    text +
                    "</p></html></div>";
        }
        return fullHTML;
    }

    //
    public static String getHTML(ToolTipStrings toolTipStrings) {
        return getHTML(toolTipStrings.title, toolTipStrings.description);
    }

    public static JToolTip getStyle (JToolTip buffTip) {
        buffTip = new JToolTip();
        buffTip.setSize(TOOLTIP_WIDTH, TOOLTIP_HEIGHT);
        buffTip.setBackground(GameColors.toolTip1);
        buffTip.setFont(new Font("Monospaced", Font.PLAIN, 13));
        buffTip.setForeground(Color.WHITE);

        LineBorder tipLine = new LineBorder(GameColors.toolTip2, 5, true);
        EtchedBorder outside = new EtchedBorder(EtchedBorder.LOWERED);
        CompoundBorder tipC = new CompoundBorder(outside, tipLine);
        EtchedBorder tipEb = new EtchedBorder(EtchedBorder.RAISED);
        CompoundBorder tipCB = new CompoundBorder(tipC, tipEb);
        buffTip.setBorder(tipCB);

        buffTip.setVisible(true);
        buffTip.setEnabled(true);
        return buffTip;
    }

    public static String getBlockTipText() {
        return getHTML(ToolTipStrings.blockStrings());
    }

    public static String getEndTurnTipText() {
        return getHTML(ToolTipStrings.endTurnStrings());
    }

    public static String getEnergyPanelText() {
        return getHTML(ToolTipStrings.energyPanelStrings());
    }
}
