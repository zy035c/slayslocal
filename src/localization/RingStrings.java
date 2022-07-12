package localization;

public class RingStrings {
    public String NAME;
    public String FLAVOR;
    public String[] DESCRIPTIONS = new String[0];

    public static RingStrings getRingStrings(String ringId) {
        RingStrings ringStrings = new RingStrings();
        return ringStrings;
    }
}
