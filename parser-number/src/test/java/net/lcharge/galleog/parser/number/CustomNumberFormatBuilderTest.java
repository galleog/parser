package net.lcharge.galleog.parser.number;

import static net.lcharge.galleog.parser.number.PrePostfixNumberFormatBuilder.DEFAULT_DECIMAL_SEPARATOR;
import static net.lcharge.galleog.parser.number.PrePostfixNumberFormatBuilder.DEFAULT_GROUPING_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Tests for {@link PrePostfixNumberFormatBuilder}.
 *
 * @author Oleg_Galkin
 */
class CustomNumberFormatBuilderTest {
    private CustomNumberFormatBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new CustomNumberFormatBuilder();
    }

    /**
     * {@link CustomNumberFormatBuilder#setGroupingChar(Character)}
     * should correctly set the grouping separator.
     */
    @Test
    void shouldSetGroupingChar() {
        var ch = ';';
        builder.setGroupingChar(ch);
        assertThat(builder.getDecimalFormatSymbols().getGroupingSeparator()).isEqualTo(ch);

        builder.setGroupingChar(null);
        assertThat(builder.getDecimalFormatSymbols().getGroupingSeparator())
                .isEqualTo(DEFAULT_GROUPING_SEPARATOR);

        builder.setDecimalChar(DEFAULT_GROUPING_SEPARATOR);
        builder.setGroupingChar(null);
        assertThat(builder.getDecimalFormatSymbols().getGroupingSeparator())
                .isEqualTo(DEFAULT_DECIMAL_SEPARATOR);
    }

    /**
     * {@link CustomNumberFormatBuilder#setDecimalChar(Character)}
     * should correctly set the decimal separator.
     */
    @Test
    void shouldSetDecimalChar() {
        var ch = ' ';
        builder.setDecimalChar(ch);
        assertThat(builder.getDecimalFormatSymbols().getDecimalSeparator()).isEqualTo(ch);

        builder.setDecimalChar(null);
        assertThat(builder.getDecimalFormatSymbols()
                .getDecimalSeparator()).isEqualTo(DEFAULT_DECIMAL_SEPARATOR);

        builder.setGroupingChar(DEFAULT_DECIMAL_SEPARATOR);
        builder.setDecimalChar(null);
        assertThat(builder.getDecimalFormatSymbols().getDecimalSeparator())
                .isEqualTo(DEFAULT_GROUPING_SEPARATOR);
    }

    /**
     * {@link CustomNumberFormatBuilder#buildNumberFormat()} should correctly build a format
     * to parse numbers with default separators
     */
    @Test
    void shouldBuildNumberFormatWithDefaultSeparators() throws ParseException {
        var format = builder.buildNumberFormat();
        assertThat(format.parse("0.12")).isEqualTo(new BigDecimal("0.12"));
        assertThat(format.parse("1,234.000")).isEqualTo(new BigDecimal("1234.000"));
        assertThat(format.parse("1,234,000")).isEqualTo(new BigDecimal("1234000"));
        assertThat(format.parse("-1234.56")).isEqualTo(new BigDecimal("-1234.56"));
    }

    /**
     * {@link CustomNumberFormatBuilder#buildNumberFormat()} should correctly build a format
     * to parse numbers with non-default separators
     */
    @Test
    void shouldBuildNumbersWithNonDefaultSeparators() throws ParseException {
        builder.setGroupingChar(' ');
        builder.setDecimalChar(',');
        var format = builder.buildNumberFormat();
        assertThat(format.parse("0,123")).isEqualTo(new BigDecimal("0.123"));
        assertThat(format.parse("1 234,56")).isEqualTo(new BigDecimal("1234.56"));
        assertThat(format.parse("-1 234 567,89")).isEqualTo(new BigDecimal("-1234567.89"));
    }
}