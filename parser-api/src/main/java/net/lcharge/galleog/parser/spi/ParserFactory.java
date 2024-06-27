package net.lcharge.galleog.parser.spi;

import net.lcharge.galleog.parser.api.Parser;

/**
 * Factory to create a {@link Parser}.
 *
 * @param <T> the type of objects the created parser produces
 * @author Oleg_Galkin
 */
public interface ParserFactory<T> {
    /**
     * Creates a parser.
     *
     * @return the created {@link Parser}
     */
    Parser<T> createParser();
}