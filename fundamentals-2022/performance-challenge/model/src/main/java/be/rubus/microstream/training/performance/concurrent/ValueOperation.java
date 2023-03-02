package be.rubus.microstream.training.performance.concurrent;

/**
 * Operation which returns a value, used by {@link ReadWriteLocked}.
 *
 * @param T the return type
 */
@FunctionalInterface
public interface ValueOperation<T> {
    /**
     * Execute an arbitrary operation and return the result
     *
     * @return the result of the operation
     */
    T execute();
}
