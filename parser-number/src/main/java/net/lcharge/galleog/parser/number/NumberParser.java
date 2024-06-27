package net.lcharge.galleog.parser.number;

import lombok.extern.slf4j.Slf4j;
import net.lcharge.galleog.parser.api.Ordered;
import net.lcharge.galleog.parser.api.Parser;

import java.util.regex.Pattern;

/**
 * {@link Parser} for decimal numbers.
 *
 * @author Oleg_Galkin
 */
@Slf4j
public class NumberParser extends AbstractNumberParser implements Ordered {
    private static final int ORDER = 200;

    private static final String PREFIX = "prefix";
    private static final String POSTFIX = "postfix";
    private static final String NUM = "num";
    private static final String GSEP = "gsep";
    private static final String DSEP = "dsep";
    private static final String REGEX =
            // prefix allows currency symbols, plus, minus, open parenthesis and optional spaces
            "^(?<" + PREFIX + ">([($€+-][ ]?){0,2})"
                    // number part allows only digits separated by an optional grouping
                    // character and a decimal character. The grouping character should be a dot,
                    // comma, or space. The grouping and decimal characters should be different
                    + "(?<" + NUM + ">(0|([1-9]\\d{0,2}(?<" + GSEP + ">[., ]))((\\d{3}\\k<" + GSEP
                    + ">)*(\\d{3}(?!\\k<" + GSEP + ">)))|[1-9]\\d*)(?<" + DSEP + ">[.,])?\\d*)"
                    // prefix allows currency symbols, plus, minus, close parenthesis and
                    // optional spaces
                    + "(?<" + POSTFIX + ">([ ]?[)$€+-][ ]?){0,2})$";
    private final Pattern pattern;

    public NumberParser() {
        this.pattern = Pattern.compile(REGEX);
    }

    @Override
    protected NumberFormatBuilder getNumberFormatBuilder(String text) {
        var matcher = this.pattern.matcher(text);
        if (!matcher.matches()) {
            logger.debug("Test '{}' doesn't match the regex", text);
            return null;
        }

        var builder = new PrePostfixNumberFormatBuilder();
        builder.setPrefix(matcher.group(PREFIX));
        builder.setPostfix(matcher.group(POSTFIX));

        var groupingSeparator = matcher.group(GSEP);
        builder.setGroupingChar(groupingSeparator == null ? null : groupingSeparator.charAt(0));

        var decimalSeparator = matcher.group(DSEP);
        builder.setDecimalChar(decimalSeparator == null ? null : decimalSeparator.charAt(0));

        builder.setNumberToParse(matcher.group(NUM));

        return builder;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
