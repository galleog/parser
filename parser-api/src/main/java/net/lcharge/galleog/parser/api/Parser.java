package net.lcharge.galleog.parser.api;

/**
 * Interface to parse text strings and converts them to other objects.
 *
 * @param <T> the type of objects this parser produces
 * @author Oleg_Galkin
 */
public interface Parser<T> {
    /**
     * Parses a text string to produce a {@code T}.
     *
     * @param text the text string
     * @return an instance of {@code T} or {@code null} if the string can't be parsed
     */
    T parse(String text);
}
