package net.lcharge.galleog.parser.card.api;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * {@code Card} describes a credit/debit card.
 *
 * @author Oleg_Galkin
 */
@Slf4j
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Card {
    private final CardType type;
    private final String number;

    /**
     * Creates an instance by a card number.
     *
     * @param number the card number
     * @return the created instance or {@code null} if the card number is invalid
     */
    public static Card of(String number) {
        var removed = StringUtils.remove(number, StringUtils.SPACE);
        var type = CardType.getType(removed);

        if (type == null) {
            logger.debug("Card number {} is invalid", number);
        }

        return type == null ? null : new Card(type, removed);
    }
}
