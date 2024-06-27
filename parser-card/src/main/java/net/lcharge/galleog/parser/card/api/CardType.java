package net.lcharge.galleog.parser.card.api;

import java.util.regex.Pattern;

/**
 * Enumerator for card types.
 *
 * @author Oleg_Galkin
 */
public enum CardType {
    VISA("^4[0-9]{12}(?:[0-9]{3})?$"),
    MASTERCARD("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$");

    private final Pattern pattern;

    CardType(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    /**
     * Gets a card type by a card number.
     *
     * @param card the card number
     * @return the card type that matches the number
     */
    public static CardType getType(String card) {
        for (var type : CardType.values()) {
            if (type.matches(card)) {
                return type;
            }
        }

        return null;
    }

    private boolean matches(String card) {
        var matcher = this.pattern.matcher(card);
        return matcher.matches();
    }
}
