package net.lcharge.galleog.parser.card.api;

import static net.lcharge.galleog.parser.card.api.CardType.MASTERCARD;
import static net.lcharge.galleog.parser.card.api.CardType.VISA;
import static net.lcharge.galleog.parser.card.api.CardType.getType;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CardType}.
 *
 * @author Oleg_Galkin
 */
class CardTypeTest {
    /**
     * {@link CardType#getType(String)} should correctly find out car types.
     */
    @Test
    void shouldFindCardType() {
        var visa = "4444444444444448";
        assertThat(getType(visa)).isEqualTo(VISA);

        var masterCard = "5500005555555559";
        assertThat(getType(masterCard)).isEqualTo(MASTERCARD);

        var invalidVisa = "4444 4444 4444 4448";
        assertThat(getType(invalidVisa)).isNull();

        var invalidMasterCard = "550000555555555";
        assertThat(getType(invalidMasterCard)).isNull();
    }
}