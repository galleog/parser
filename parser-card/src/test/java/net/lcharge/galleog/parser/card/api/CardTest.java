package net.lcharge.galleog.parser.card.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Card}.
 *
 * @author Oleg_Galkin
 */
class CardTest {
    /**
     * {@link Card#of(String)} should correctly creates {@link Card cards}.
     */
    @Test
    void shouldCreateCard() {
        var visa = "4444 4400 8954 4345";
        var card = Card.of(visa);

        assertThat(card).isNotNull();
        assertThat(card.getType()).isEqualTo(CardType.VISA);
        assertThat(card.getNumber()).isEqualTo(StringUtils.remove(visa, StringUtils.SPACE));
    }
}