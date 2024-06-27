package net.lcharge.galleog.parser.card;

import net.lcharge.galleog.parser.api.Parser;
import net.lcharge.galleog.parser.card.api.Card;
import net.lcharge.galleog.parser.spi.ParserFactory;

/**
 * {@link ParserFactory} for credit/debit cards.
 *
 * @author Oleg_Galkin
 */
public class CardParserFactory implements ParserFactory<Card> {
    @Override
    public Parser<Card> createParser() {
        return new  CardParser();
    }
}
