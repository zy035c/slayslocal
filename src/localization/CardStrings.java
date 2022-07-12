package localization;

public class CardStrings {
    public String NAME;
    public String FLAVOR;
    public String[] DESCRIPTIONS = new String[0];

    public static CardStrings getCardStrings(String cardId) {
        CardStrings cardStrings = new CardStrings();
        return cardStrings;
    }



}