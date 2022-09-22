package be.rubus.microstream.training.performance.microstream.utils;

import one.microstream.reference.Lazy;

import java.util.Optional;

/**
 * {@link Lazy} reference utilities
 */
public final class LazyUtils {

    private LazyUtils() {
    }

    /**
     * Clears a {@link Lazy} reference if it is not <code>null</code> and stored.
     *
     * @param <T>
     * @param lazy the reference, may be <code>null</code>
     * @return the optional content of the lazy reference when it was cleared successfully
     * @see Lazy#isStored()
     * @see Lazy#clear()
     */
    public static <T> Optional<T> clearIfStored(Lazy<T> lazy) {
        return lazy != null && lazy.isStored()
                ? Optional.ofNullable(lazy.clear())
                : Optional.empty()
                ;
    }
}
