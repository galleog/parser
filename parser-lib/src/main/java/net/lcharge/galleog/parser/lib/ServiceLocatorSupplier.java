package net.lcharge.galleog.parser.lib;

import lombok.SneakyThrows;
import net.lcharge.galleog.parser.api.OrderComparator;
import net.lcharge.galleog.parser.api.Parser;
import net.lcharge.galleog.parser.spi.ParserFactory;
import org.apache.commons.lang3.concurrent.AtomicSafeInitializer;
import org.apache.commons.lang3.concurrent.ConcurrentException;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * {@link Supplier} of available parsers that use the standard java {@link ServiceLoader}.
 *
 * @author Oleg_Galkin
 */
public class ServiceLocatorSupplier implements Supplier<Iterable<? extends Parser<?>>> {
    private final ChainInitializer initializer = new ChainInitializer();

    @Override
    @SneakyThrows(ConcurrentException.class)
    public Iterable<? extends Parser<?>> get() {
        return initializer.get();
    }

    private static final class ChainInitializer
            extends AtomicSafeInitializer<List<? extends Parser<?>>> {
        @Override
        protected List<? extends Parser<?>> initialize() {
            return ServiceLoader.load(ParserFactory.class)
                    .stream()
                    .map(ServiceLoader.Provider::get)
                    .map(factory -> (Parser<?>) factory.createParser())
                    .sorted(OrderComparator.INSTANCE)
                    .toList();
        }
    }
}
