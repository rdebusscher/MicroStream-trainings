package be.rubus.microstream.training.performance.microstream.database;

import be.rubus.microstream.training.performance.concurrent.ReadWriteLocked;
import be.rubus.microstream.training.performance.microstream.database.model.InventoryItem;
import be.rubus.microstream.training.performance.microstream.database.model.Shop;
import one.microstream.persistence.types.Persister;
import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * All retail shops operated by this company.
 * <p>
 * This type is used to read and write the {@link Shop}s, their {@link Employee}s and {@link Inventory}s.
 * <p>
 * All operations on this type are thread safe.
 *
 * @see Data#shops()
 * @see ReadWriteLocked
 */
public class Shops extends ReadWriteLocked {
    /**
     * Simple list to hold the shops.
     */
    private final List<Shop> shops = new ArrayList<>(1024);

    public Shops() {
        super();
    }

    /**
     * Adds a range of new shops and stores it with the given persister.
     *
     * @param shops     the new shops
     * @param persister the persister to store them with
     */
    public void addAll(Collection<? extends Shop> shops, Persister persister) {
        write(() -> {
            this.shops.addAll(shops);
            persister.store(this.shops);
        });
    }

    /**
     * Gets the total amount of all shops.
     *
     * @return the amount of shops
     */
    public int shopCount() {
        return read(shops::size);
    }

    /**
     * Gets all shops as a {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all shops
     */
    public List<Shop> all() {
        return read(() -> new ArrayList<>(shops));
    }

    /**
     * Clears all {@link Lazy} references used by all shops.
     * This frees the used memory but you do not lose the persisted data. It is loaded again on demand.
     *
     * @see Shop#clear()
     */
    public void clear() {
        write(() -> shops.forEach(Shop::clear));
    }

    /**
     * Executes a function with a {@link Stream} of {@link Shop}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T compute(Function<Stream<Shop>, T> streamFunction) {
        return read(() -> streamFunction.apply(shops.parallelStream()));
    }

    /**
     * Executes a function with a {@link Stream} of {@link InventoryItem}s and returns the computed value.
     *
     * @param <T>      the return type
     * @param function computing function
     * @return the computed result
     */
    public <T> T computeInventory(Function<Stream<InventoryItem>, T> function) {
        return read(() -> function.apply(shops.parallelStream().flatMap(shop -> shop.inventory().compute(entries -> entries.map(entry -> new InventoryItem(shop, entry.getKey(), entry.getValue()))))));
    }

    /**
     * Gets the shop with a specific name or <code>null</code> if none was found.
     *
     * @param name the name to search by
     * @return the matching shop or <code>null</code>
     */
    public Shop ofName(String name) {
        return read(() -> shops.stream().filter(shop -> shop.name().equals(name)).findAny().orElse(null));
    }

}
