package net.lcharge.galleog.parser.number;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import java.text.NumberFormat;

/**
 * {@link NumberFormatBuilder} that allows setting prefix and postfix parts of
 * the test string to parse.
 *
 * @author Oleg_Galkin
 */
@Slf4j
public class PrePostfixNumberFormatBuilder extends CustomNumberFormatBuilder {
    private static final char DOLLAR = '$';
    private static final char EURO = '€';
    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char OPEN_PARENTHESIS = '(';
    private static final char CLOSE_PARENTHESIS = ')';
    private static final char SPACE = ' ';

    private Boolean negative;
    private boolean currencySet;
    private boolean openParenthesis;
    private boolean closeParenthesis;

    private boolean invalid;

    /**
     * Sets the prefix for the text string to parse
     *
     * @param prefix the optional prefix that can contain additional currency symbols,
     *               plus, minus, and an open parenthesis
     */
    public void setPrefix(String prefix) {
        if (prefix.isBlank()) {
            return;
        }

        for (var i = 0; i < prefix.length(); i++) {
            if (this.invalid) {
                return;
            }

            var ch = prefix.charAt(i);
            validateChar(ch);
            if (ch == OPEN_PARENTHESIS) {
                if (this.negative != null || this.openParenthesis) {
                    // '+', '-', or '(' symbols have been found
                    logger.debug("Prefix '{}' is invalid. An open parenthesis is duplicated or"
                            + " a number sign has been set", prefix);
                    this.invalid = true;
                    return;
                }

                this.openParenthesis = true;
                if (this.closeParenthesis) {
                    this.negative = true;
                }
            } else if (ch == CLOSE_PARENTHESIS) {
                // close parenthesis shouldn't occur in prefix
                logger.debug("Prefix '{}' contains a close parenthesis", prefix);
                this.invalid = true;
            }
        }
    }

    /**
     * Sets the postfix for the text string to parse
     *
     * @param postfix the optional postfix that can contain additional currency symbols,
     *                plus, minus, and a close parenthesis
     */
    public void setPostfix(String postfix) {
        if (postfix.isBlank()) {
            return;
        }

        for (var i = 0; i < postfix.length(); i++) {
            if (this.invalid) {
                return;
            }

            var ch = postfix.charAt(i);
            validateChar(ch);
            if (ch == CLOSE_PARENTHESIS) {
                if (this.negative != null || this.closeParenthesis) {
                    // '+', '-', or ')' symbols have been found
                    logger.debug("Postfix '{}' is invalid. A close parenthesis is duplicated or"
                            + " a number sign has been set", postfix);
                    this.invalid = true;
                    return;
                }

                this.closeParenthesis = true;
                if (this.openParenthesis) {
                    this.negative = true;
                }
            } else if (ch == OPEN_PARENTHESIS) {
                // open parenthesis shouldn't occur in postfix
                logger.debug("Postfix '{}' contains an open parenthesis", postfix);
                this.invalid = true;
            }
        }
    }

    private void validateChar(char ch) {
        if (ch == DOLLAR || ch == EURO) {
            if (this.currencySet) {
                // duplicate currency symbol
                logger.debug("Currency symbol is duplicated");
                this.invalid = true;
                return;
            }

            this.currencySet = true;
        } else if (ch == PLUS) {
            if (this.negative != null || this.openParenthesis || this.closeParenthesis) {
                // '+', '-', '(' or ')' symbols have been found
                logger.debug("Number sign is duplicated");
                this.invalid = true;
                return;
            }

            this.negative = false;
        } else if (ch == MINUS) {
            if (this.negative != null || this.openParenthesis || this.closeParenthesis) {
                // '+', '-', '(' or ')' symbols have been found
                logger.debug("Number sign is duplicated");
                this.invalid = true;
                return;
            }

            this.negative = true;
        } else if (ch != SPACE && ch != OPEN_PARENTHESIS && ch != CLOSE_PARENTHESIS) {
            // '$', '€', '+', '-', '(', ')', and ' ' are only allowed
            logger.debug("Invalid symbol '{}' is found", ch);
            this.invalid = true;
        }

        if (this.openParenthesis && this.closeParenthesis) {
            this.negative = true;
        }
    }

    @Override
    public String getNumberToParse() {
        var number = super.getNumberToParse();
        return BooleanUtils.isTrue(this.negative) ? DEFAULT_MINUS_SIGN + number : number;
    }

    @Override
    public NumberFormat buildNumberFormat() {
        if (this.openParenthesis != this.closeParenthesis) {
            logger.debug("Parentheses aren't paired");
            return null;
        }

        return this.invalid ? null : super.buildNumberFormat();
    }
}
