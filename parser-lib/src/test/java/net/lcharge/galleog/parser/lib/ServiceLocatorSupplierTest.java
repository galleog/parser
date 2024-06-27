package net.lcharge.galleog.parser.lib;

import static org.assertj.core.api.Assertions.assertThat;

import net.lcharge.galleog.parser.card.CardParser;
import net.lcharge.galleog.parser.number.NumberParser;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ServiceLocatorSupplier}.
 *
 * @author Oleg_Galkin
 */
class ServiceLocatorSupplierTest {
    /**
     * {@link ServiceLocatorSupplier#get()} should load {@link CardParser} and {@link NumberParser}.
     */
    @Test
    void shouldLoadCardAndNumberParsers() {
        var parsers = new ServiceLocatorSupplier().get();

        assertThat(parsers).hasSize(2);
        assertThat(parsers).element(0).isExactlyInstanceOf(CardParser.class);
        assertThat(parsers).element(1).isExactlyInstanceOf(NumberParser.class);
    }
}