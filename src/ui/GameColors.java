package ui;

import java.awt.*;

public class GameColors {

    // 获得html String，html格式的颜色
    public static String getHTMLColorString(Color color) {
        String red = Integer.toHexString(color.getRed());
        String green = Integer.toHexString(color.getGreen());
        String blue = Integer.toHexString(color.getBlue());

        return "#" +
                (red.length() == 1? "0" + red : red) +
                (green.length() == 1? "0" + green : green) +
                (blue.length() == 1? "0" + blue : blue);
    }

    public static Color card_common = new Color(187, 181, 184, 213); // card title background
    public static Color card_uncommon = new Color(125, 218, 224, 255); // card title background
    public static Color card_rare = new Color(240, 195, 73, 255); // card title background

    public static Color grey1 = new Color(215, 198, 172); // card background @test

    public static Color green1 = new Color(175, 210, 158); // ready to play
    public static Color green2 = new Color(100,200,50); // release to play

    public static Color purple1 = new Color(215, 50 ,255); // buff

    public static Color pile1 = new Color(218, 216, 201, 255); // for draw pile
    public static Color pile1border = new Color(183, 78, 30, 255); // for draw pile
    public static Color pile1shadow = new Color(109, 104, 88, 255); // for draw pile
    public static Color pile2 = new Color(139, 181, 208, 255); // for discard pile
    public static Color pile2border = new Color(62, 91, 108, 255); // for discard pile
    public static Color pile2shadow = new Color(50, 76, 89, 255); // for discard pile
    public static Color dark1 = new Color(66, 66, 70, 255); // for exhaust pile

    public static Color energyBorder1 = Color.BLACK;
    public static Color energyBorder2 = new Color(199, 199, 182, 255);
    public static Color energyBorder3 = new Color(164, 41, 40, 216);
    public static Color energy4 = new Color(236, 165, 58, 255);

    public static Color shieldBlue = new Color(144, 201, 239, 255);

    public static Color orange3 = new Color(154, 141, 113, 255); // for card content

    public static Color card_highlight = new Color(140, 204, 203, 255); // for card body
    public static Color card_red = new Color(210, 76, 78, 255); // for card body
    public static Color card_red2 = new Color(169, 77, 75, 255);
    public static Color card_red3 = new Color(91, 49, 56, 255);
    public static Color card_red4 = new Color(148, 46, 48, 255);
    public static Color card_red5 = new Color(128, 87, 82, 255); // for card description

    public static Color card_green = new Color(111, 196, 97, 255); // for card body
    public static Color card_green2 = new Color(85, 89, 73, 255); // for card description

    public static Color card_colorless = new Color(171, 171, 171, 245);
    public static Color card_colorless2 = new Color(120, 120, 120, 255);

    public static Color mainText = new Color(245, 210, 117, 255);

    public static Color toolTipTitle = new Color(236, 193, 48, 255);
    public static Color toolTip1 = new Color(47, 58, 58, 255);
    public static Color toolTip2 = new Color(116, 139, 140, 255);

    // buff bar and ring bar
    public static Color buffNumber = new Color(0, 252, 0, 255);
    public static Color testBuff = new Color(245, 212, 81, 255);
    public static Color testBuff2 = new Color(255, 135, 0, 255);
    public static Color testBuff3 = new Color(255, 45, 0, 255);
    public static Color testRing = new Color(125, 138, 145, 255);
    public static Color testRing2 = new Color(178, 187, 189, 255);

    // topbar
    public static Color topMenuBar = new Color(115, 130, 140, 255);
    public static Color topBarShadow = new Color(48, 56, 57, 255);
    public static Color topBarSpaceholder = new Color(136, 151, 164, 255);
    public static Color topHPBar = new Color(249, 140, 130, 255);
    public static Color heart = new Color(224, 65, 39, 255);

    // cgui
    public static Color healthBar = new Color(204, 18, 22, 255);
    public static Color healthBar2 = new Color(255, 0, 0, 255);
    public static Color cguiPlaceholder = new Color(236, 220, 184, 255);

    public static Color gameBackground = new Color(204, 203, 201, 255);

}
