package net.lcharge.galleog.parser.lib;

import lombok.extern.slf4j.Slf4j;
import net.lcharge.galleog.parser.api.Parser;

import java.util.function.Supplier;

/**
 * {@link Parser} that uses a chain of other parsers.
 * <p>
 * <p/>The {@link Supplier} of available parsers can be passed as an argument when constructing
 * a class instance. By default {@link ServiceLocatorSupplier} is used
 *
 * @author Oleg_Galkin
 * @see ServiceLocatorSupplier
 */
@Slf4j
public class CompositeParser implements Parser<Object> {
    private final Supplier<Iterable<? extends Parser<?>>> parsersSupplier;

    /**
     * Constructs an instance with {@link ServiceLocatorSupplier}.
     *
     * @see ServiceLocatorSupplier
     */
    public CompositeParser() {
        this(new ServiceLocatorSupplier());
    }

    /**
     * Constructs an instance by the specified supplier.
     *
     * @param supplier the supplier that gets an {@link Iterable} of
     *                 available {@link Parser Parsers}
     */
    public CompositeParser(Supplier<Iterable<? extends Parser<?>>> supplier) {
        this.parsersSupplier = supplier;
    }

    @Override
    public Object parse(String text) {
        for (var parser : parsersSupplier.get()) {
            var result = parser.parse(text);
            if (result != null) {
                logger.debug("{} parser was used to parse text '{}'",
                        parser.getClass().getName(), text);
                return result;
            }
        }

        return null;
    }
}
