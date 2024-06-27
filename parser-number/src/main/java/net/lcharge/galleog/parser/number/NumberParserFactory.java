package net.lcharge.galleog.parser.number;

import net.lcharge.galleog.parser.api.Parser;
import net.lcharge.galleog.parser.spi.ParserFactory;

/**
 * {@link ParserFactory} for numbers.
 *
 * @author Oleg_Galkin
 */
public class NumberParserFactory implements ParserFactory<Number> {
    @Override
    public Parser<Number> createParser() {
        return new NumberParser();
    }
}
