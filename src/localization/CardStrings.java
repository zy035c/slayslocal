package localization;

public class CardStrings extends LanguageStrings {
    public String NAME;
    public String FLAVOR;
    public String[] DESCRIPTIONS = new String[0];
    public String description;

    public static CardStrings getCardStrings(String cardId) {
        CardStrings cardStrings = new CardStrings();
        return cardStrings;
    }

    public static CardStrings barricade() {
        CardStrings barricade = new CardStrings();
        barricade.description = "Block is not removed at\n" +
                "the start of your turn.";
        return barricade;
    }

    public static CardStrings bodySlam() {
        CardStrings bodySlam = new CardStrings();
        bodySlam.NAME = "Body Slam";
        bodySlam.description = "Deal damage equal to\n" +
                "Your Block."; // plain text
        return bodySlam;
    }

    public static CardStrings inflame() {
        CardStrings inflame = new CardStrings();
        inflame.NAME = "Inflame";
        inflame.description = "Gain !MAGIC! Strength.";
        return inflame;
    }

    public static CardStrings cleave() {
        CardStrings cleave = new CardStrings();
        cleave.NAME = "Cleave";
        cleave.description = "Deal !DAMAGE! to ALL enemies";
        return cleave;
    }

    public static CardStrings clothesline() {
        CardStrings clothesline = new CardStrings();
        clothesline.NAME = "Clothesline";
        clothesline.description = "Deal !DAMAGE! damage.\n" +
                "Apply !MAGIC! Weak.";
        return clothesline;
    }
}