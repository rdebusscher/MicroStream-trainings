package be.rubus.microstream.training.performance.microstream.utils;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Various collection utilities.
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Returns a parallel {@link Stream} of the given collection
     * or an empty {@link Stream} if the collection is <code>null</code>.
     *
     * @param <T>
     * @param <C>
     * @param collection a collection or <code>null</code>
     * @return a parallel {@link Stream} backed by the collection or an empty one
     */
    public static <T, C extends Collection<T>> Stream<T> ensureParallelStream(
            C collection
    ) {
        return collection == null
                ? Stream.empty()
                : collection.parallelStream();
    }

}
