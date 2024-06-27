package net.lcharge.galleog.parser.number;

import net.lcharge.galleog.parser.api.Parser;

import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * Abstract parser for numbers, providing a {@link #getNumberFormatBuilder(String)} template method.
 *
 * @author Oleg_Galkin
 */
public abstract class AbstractNumberParser implements Parser<Number> {
    /**
     * Gets a concrete builder to build {@link NumberFormat}.
     *
     * @param text the text string that should be parsed
     * @return the {@link NumberFormatBuilder} instance or
     * {@code null} if the builder can't be created
     */
    protected abstract NumberFormatBuilder getNumberFormatBuilder(String text);

    @Override
    public Number parse(String text) {
        var builder = getNumberFormatBuilder(text);
        if (builder == null) {
            return null;
        }

        var format = builder.buildNumberFormat();
        if (format == null) {
            return null;
        }

        var position = new ParsePosition(0);
        var number = builder.getNumberToParse();
        var res = format.parse(number, position);
        if (position.getErrorIndex() != -1 || number.length() != position.getIndex()) {
            return null;
        }

        return res;
    }
}
