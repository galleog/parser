package net.lcharge.galleog.parser.number;

import java.text.NumberFormat;

/**
 * Interface to build {@link NumberFormat}
 *
 * @author Oleg_Galkin
 */
public interface NumberFormatBuilder {
    /**
     * Gets a text string to parse using the built {@link NumberFormat}.
     */
    String getNumberToParse();

    /**
     * Builds an instance of {@link NumberFormat} to parse numbers.
     *
     * @return the built {@link NumberFormat} or {@code null} if it can't be created
     */
    NumberFormat buildNumberFormat();
}
