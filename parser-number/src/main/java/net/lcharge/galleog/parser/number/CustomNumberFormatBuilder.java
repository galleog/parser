package net.lcharge.galleog.parser.number;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * {@link NumberFormatBuilder} that allows setting the grouping and decimal separators
 * to parse text with custom symbols.
 *
 * @author Oleg_Galkin
 */
@Slf4j
public class CustomNumberFormatBuilder implements NumberFormatBuilder {
    protected static final char DEFAULT_GROUPING_SEPARATOR = ',';
    protected static final char DEFAULT_DECIMAL_SEPARATOR = '.';
    protected static final char DEFAULT_MINUS_SIGN = '-';

    private char groupingChar = DEFAULT_GROUPING_SEPARATOR;
    private char decimalChar = DEFAULT_DECIMAL_SEPARATOR;
    private String number;

    @Override
    public String getNumberToParse() {
        return this.number;
    }

    /**
     * Sets the text string to parse using the built {@link NumberFormat}.
     */
    public void setNumberToParse(String number) {
        Validate.notBlank(number);
        this.number = number;
    }

    /**
     * Sets the character to be used as the grouping separator in numbers.
     *
     * @param ch the grouping separator or {@code null} if the default one (comma) should be used
     */
    public final void setGroupingChar(Character ch) {
        if (ch != null) {
            this.groupingChar = ch;
        } else if (this.decimalChar == DEFAULT_GROUPING_SEPARATOR) {
            this.groupingChar = DEFAULT_DECIMAL_SEPARATOR;
        } else {
            this.groupingChar = DEFAULT_GROUPING_SEPARATOR;
        }
    }

    /**
     * Sets the character to be used as the decimal separator in numbers.
     *
     * @param ch the decimal separator or {@code null} if the default one (dot) should be used
     */
    public final void setDecimalChar(Character ch) {
        if (ch != null) {
            this.decimalChar = ch;
        } else if (this.groupingChar == DEFAULT_DECIMAL_SEPARATOR) {
            this.decimalChar = DEFAULT_GROUPING_SEPARATOR;
        } else {
            this.decimalChar = DEFAULT_DECIMAL_SEPARATOR;
        }
    }

    /**
     * Gets an instance of {@link DecimalFormatSymbols} that should be used to parse numbers.
     * <p>
     * <p/> Default implementation just defines the grouping separator, decimal separator,
     * and default minus sign.
     */
    protected DecimalFormatSymbols getDecimalFormatSymbols() {
        var symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(this.groupingChar);
        symbols.setDecimalSeparator(this.decimalChar);
        symbols.setMinusSign(DEFAULT_MINUS_SIGN);
        return symbols;
    }

    @Override
    public NumberFormat buildNumberFormat() {
        var format = NumberFormat.getInstance();
        if (!(format instanceof DecimalFormat decimalFormat)) {
            logger.warn("{} isn't an instance of DecimalFormat", format);
            return null;
        }

        decimalFormat.setParseBigDecimal(true);
        decimalFormat.setDecimalFormatSymbols(getDecimalFormatSymbols());
        logger.debug("'{}' symbol is used as a grouping separator;"
                + " '{}' is used as a decimal separator", this.groupingChar, this.decimalChar);

        return decimalFormat;
    }
}
