package net.lcharge.galleog.parser.card;

import net.lcharge.galleog.parser.api.Ordered;
import net.lcharge.galleog.parser.api.Parser;
import net.lcharge.galleog.parser.card.api.Card;

/**
 * {@link Parser} to parse numbers of credit/debit cards.
 *
 * @author Oleg_Galkin
 */
public class CardParser implements Parser<Card>, Ordered {
    private static final int ORDER = 100;

    @Override
    public Card parse(String text) {
        return Card.of(text);
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
