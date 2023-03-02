package be.rubus.microstream.training.performance.microstream.database.model;

import be.rubus.microstream.training.performance.microstream.utils.LazyUtils;
import be.rubus.microstream.training.performance.model.Address;
import be.rubus.microstream.training.performance.model.Employee;
import be.rubus.microstream.training.performance.model.Inventory;
import be.rubus.microstream.training.performance.model.NamedWithAddress;
import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Shop entity which holds a name, {@link Address}, {@link Employee}s and an {@link Inventory}.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Shop extends NamedWithAddress {
    private final List<Employee> employees;
    private final Lazy<Inventory> inventory;

    /**
     * Constructor to create a new {@link Shop} instance.
     *
     * @param name    not empty
     * @param address not <code>null</code>
     */
    public Shop(Long id, String name, Address address) {
        this(id, name, address, new ArrayList<>(), new Inventory());
    }

    /**
     * Used by  RandomDataGenerator
     */
    public Shop(Long id, String name, Address address, List<Employee> employees, Inventory inventory) {
        super(id, name, address);
        this.employees = new ArrayList<>(employees);
        this.inventory = Lazy.Reference(inventory);
    }

    /**
     * Get the employees.
     *
     * @return a {@link Stream} of {@link Employee}s
     */
    public Stream<Employee> employees() {
        return employees.stream();
    }

    /**
     * Get the inventory.
     *
     * @return the inventory
     */
    public Inventory inventory() {
        return inventory.get();
    }

    /**
     * Clears all {@link Lazy} references held by this shop.
     * This frees the used memory but you do not lose the persisted data. It is loaded again on demand.
     */
    public void clear() {
        LazyUtils.clearIfStored(this.inventory);
    }

}
