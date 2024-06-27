package net.lcharge.galleog.parser.api;

import org.apache.commons.lang3.Validate;

import java.util.Comparator;

/**
 * {@link Comparator} for {@link Ordered} objects, sorting by order value ascending.
 *
 * Any object that does not provide its own order value is implicitly assigned
 * a value of {@link Ordered#LOWEST_PRECEDENCE}, thus ending up at the end of a sorted collection
 * in arbitrary order with respect to other objects with the same order value.
 *
 * @author Oleg_Galkin
 */
public class OrderComparator implements Comparator<Object> {
    /**
     * Singleton instance of the class.
     */
    public static final OrderComparator INSTANCE = new OrderComparator();

    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare(getOrder(o1), getOrder(o2));
    }

    private int getOrder(Object obj) {
        Validate.notNull(obj, "Can't compare nulls");
        return obj instanceof Ordered ordered ? ordered.getOrder() : Ordered.LOWEST_PRECEDENCE;
    }
}
