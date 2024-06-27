package net.lcharge.galleog.parser.number;

import static org.assertj.core.api.Assertions.assertThat;

import net.lcharge.galleog.parser.api.Parser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * Tests for {@link NumberParser}.
 *
 * @author Oleg_Galkin
 */
class NumberParserTest {
    private final Parser<Number> parser = new NumberParser();

    /**
     * {@link NumberParser#parse(String)} should correctly parse texts.
     */
    @Test
    void shouldParseNumbers() {
        assertThat(parser.parse("$(0.12)")).isEqualTo(new BigDecimal("-0.12"));
        assertThat(parser.parse("1.234,5678$ +")).isEqualTo(new BigDecimal("1234.5678"));
        assertThat(parser.parse("1,234.5- $")).isEqualTo(new BigDecimal("-1234.5"));
        assertThat(parser.parse("1.234")).isEqualTo(new BigDecimal("1234"));
        assertThat(parser.parse("(1,234.€ )")).isEqualTo(new BigDecimal("-1234"));
        assertThat(parser.parse("€ 1,234.000")).isEqualTo(new BigDecimal("1234.000"));
        assertThat(parser.parse("(1,234,000)$")).isEqualTo(new BigDecimal("-1234000"));
    }

    /**
     * {@link NumberParser#parse(String)} should return {@code null} for incorrect inputs.
     */
    @Test
    void shouldFailToParseNumbers() {
        assertThat(parser.parse("")).isNull();
        assertThat(parser.parse("AF")).isNull();
        assertThat(parser.parse("00.12")).isNull();
        assertThat(parser.parse("1,23.45")).isNull();
        assertThat(parser.parse("1,234,")).isNull();
        assertThat(parser.parse("0,123.45")).isNull();
        assertThat(parser.parse("1:234.56")).isNull();
        assertThat(parser.parse("1.234,567.89")).isNull();
        assertThat(parser.parse("12,345 67")).isNull();
        assertThat(parser.parse("(123.45))")).isNull();
        assertThat(parser.parse("+(123.45)")).isNull();
        assertThat(parser.parse("-(123.45)")).isNull();
        assertThat(parser.parse("-123.45+")).isNull();
        assertThat(parser.parse("$123.45€")).isNull();
    }
}