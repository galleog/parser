package net.lcharge.galleog.parser.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.lcharge.galleog.parser.api.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * Tests for {@link CompositeParser}.
 *
 * @author Oleg_Galkin
 */
@ExtendWith(MockitoExtension.class)
class CompositeParserTest {
    public static final String TO_PARSE = "abc";

    @Mock
    private Parser<String> stringParser;

    @Mock
    private Parser<Integer> integerParser;

    private Parser<Object> compositeParser;

    @BeforeEach
    void setUp() {
        compositeParser = new CompositeParser(() -> List.of(stringParser, integerParser));
    }

    /**
     * {@link CompositeParser#parse(String)} should return the result of the first parser.
     */
    @Test
    void shouldReturnResultOfFirstParser() {
        when(stringParser.parse(anyString())).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(compositeParser.parse(TO_PARSE)).isEqualTo(TO_PARSE);

        verify(stringParser).parse(TO_PARSE);
        verify(integerParser, never()).parse(anyString());
    }

    /**
     * {@link CompositeParser#parse(String)} should return the result of the second parser
     * if the first parser failed.
     */
    @Test
    void shouldReturnResultOfSecondParser() {
        var result = 23;

        when(stringParser.parse(TO_PARSE)).thenReturn(null);
        when(integerParser.parse(TO_PARSE)).thenReturn(result);

        assertThat(compositeParser.parse(TO_PARSE)).isEqualTo(result);

        verify(stringParser).parse(TO_PARSE);
        verify(integerParser).parse(TO_PARSE);
    }

    /**
     * {@link CompositeParser#parse(String)} should return {@code null} if both parsers failed.
     */
    @Test
    void shouldFailToParseString() {
        when(stringParser.parse(TO_PARSE)).thenReturn(null);
        when(integerParser.parse(TO_PARSE)).thenReturn(null);

        assertThat(compositeParser.parse(TO_PARSE)).isNull();

        verify(stringParser).parse(TO_PARSE);
        verify(integerParser).parse(TO_PARSE);
    }
}