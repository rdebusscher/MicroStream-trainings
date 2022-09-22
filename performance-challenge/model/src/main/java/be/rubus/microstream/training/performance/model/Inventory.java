package be.rubus.microstream.training.performance.model;



import be.rubus.microstream.training.performance.concurrent.ReadWriteLocked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * Inventory entity which holds {@link Book}s and amounts of them.
 * <p>
 * All operations on this type are thread safe.
 */
public class Inventory extends ReadWriteLocked {
    private final Map<Book, Integer> inventoryMap;

    public Inventory() {
        this(new HashMap<>());
    }

    /**
     * Used by RandomDataGenerator.
     */
    public Inventory(Map<Book, Integer> inventoryMap) {

        this.inventoryMap = inventoryMap;
    }

    /**
     * Get the amount of a specific book in this inventory.
     *
     * @param book the book
     * @return the amount of the given book in this inventory or 0
     */
    public int amount(Book book) {
        return read(() -> {
            Integer result = inventoryMap.get(book);
            return result == null ? 0 : result;
        });
    }

    /**
     * Executes a function with a {@link Stream} of {@link Entry}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T compute(Function<Stream<Entry<Book, Integer>>, T> streamFunction) {
        return read(() -> streamFunction.apply(inventoryMap.entrySet().stream()));
    }

    /**
     * Get the total amount of slots (different books) in this inventory.
     *
     * @return the amount of slots
     */
    public int slotCount() {
        return read(inventoryMap::size);
    }

    /**
     * Gets all books and their amount as a {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all books and their amount
     */
    public List<Entry<Book, Integer>> slots() {
        return read(() -> new ArrayList<>(inventoryMap.entrySet()));
    }

    /**
     * Gets all books as a {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all books
     */
    public List<Book> books() {
        return read(() -> new ArrayList<>(inventoryMap.keySet()));
    }

}
