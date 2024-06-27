package net.lcharge.galleog.parser.number;

import static net.lcharge.galleog.parser.number.CustomNumberFormatBuilder.DEFAULT_MINUS_SIGN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PrePostfixNumberFormatBuilder}.
 *
 * @author Oleg_Galkin
 */
class PrePostfixNumberFormatBuilderTest {
    private static final String TEXT = "1234.56";

    private PrePostfixNumberFormatBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new PrePostfixNumberFormatBuilder();
        builder.setNumberToParse(TEXT);
    }

    /**
     * '+' in prefix shouldn't negate the number.
     */
    @Test
    void plusInPrefixShouldNotNegateNumber() {
        builder.setPrefix("+ €");
        assertThat(builder.getNumberToParse()).isEqualTo(TEXT);
        assertThat(builder.buildNumberFormat()).isNotNull();
    }

    /**
     * '+' in postfix shouldn't negate the number.
     */
    @Test
    void plusInPostfixShouldNotNegateNumber() {
        builder.setPostfix("+ ");
        assertThat(builder.getNumberToParse()).isEqualTo(TEXT);
        assertThat(builder.buildNumberFormat()).isNotNull();
    }

    /**
     * Prefix with '-' should negate the number.
     */
    @Test
    void minusInPrefixShouldNegateNumber() {
        builder.setPrefix("$ - ");
        assertThat(builder.getNumberToParse()).isEqualTo(DEFAULT_MINUS_SIGN + TEXT);
        assertThat(builder.buildNumberFormat()).isNotNull();
    }

    /**
     * '-' in postfix should negate the number.
     */
    @Test
    void minusInPostfixShouldNegateNumber() {
        builder.setPrefix("€");
        builder.setPostfix(" -");
        assertThat(builder.getNumberToParse()).isEqualTo(DEFAULT_MINUS_SIGN + TEXT);
        assertThat(builder.buildNumberFormat()).isNotNull();
    }

    /**
     * Parentheses should negate the number.
     */
    @Test
    void parenthesesShouldNegateNumber() {
        builder.setPrefix("(");
        builder.setPostfix("$)");
        assertThat(builder.getNumberToParse()).isEqualTo(DEFAULT_MINUS_SIGN + TEXT);
        assertThat(builder.buildNumberFormat()).isNotNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail if both minus and plus
     * were found.
     */
    @Test
    void shouldFailIfBothMinusAndPlusFound() {
        builder.setPrefix("+");
        builder.setPostfix("-");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if duplicate minus occurs.
     */
    @Test
    void shouldFailWithDuplicateMinus() {
        builder.setPrefix("- $ -");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if duplicate plus occurs.
     */
    @Test
    void shouldFailWithDuplicatePlus() {
        builder.setPostfix("+ +");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if duplicate currency symbols occurs.
     */
    @Test
    void shouldFailWithDuplicateCurrencies() {
        builder.setPostfix("$");
        builder.setPostfix("€");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if parentheses aren't paired.
     */
    @Test
    void shouldFailIfParenthesesAreNotPaired() {
        builder.setPrefix("(");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * with a duplicate open parenthesis.
     */
    @Test
    void shouldFailWithDuplicateOpenParenthesis() {
        builder.setPrefix("( - (");
        builder.setPostfix(")");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * with a duplicate close parenthesis.
     */
    @Test
    void shouldFailWithDuplicateCloseParenthesis() {
        builder.setPrefix("(");
        builder.setPostfix(") $ )");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if a close parenthesis is in prefix.
     */
    @Test
    void shouldFailIfCloseParenthesisIsInPrefix() {
        builder.setPrefix("()");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if an open parenthesis is in postfix.
     */
    @Test
    void shouldFailIfOpenParenthesisIsInPostfix() {
        builder.setPostfix("()");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if an unknown symbol is in prefix.
     */
    @Test
    void shouldFailIfUnknownSymbolIsInPrefix() {
        builder.setPrefix("- s");
        assertThat(builder.buildNumberFormat()).isNull();
    }

    /**
     * {@link PrePostfixNumberFormatBuilder#buildNumberFormat()} should fail
     * if an unknown symbol is in postfix.
     */
    @Test
    void shouldFailIfUnknownSymbolIsInPosfix() {
        builder.setPostfix("e");
        assertThat(builder.buildNumberFormat()).isNull();
    }
}