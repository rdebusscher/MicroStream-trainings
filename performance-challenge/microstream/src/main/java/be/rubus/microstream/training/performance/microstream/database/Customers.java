package be.rubus.microstream.training.performance.microstream.database;

import be.rubus.microstream.training.performance.concurrent.ReadWriteLocked;
import be.rubus.microstream.training.performance.model.Customer;
import one.microstream.persistence.types.Persister;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * All registered customers of this company.
 * <p>
 * This type is used to read and write the {@link Customer}s.
 * <p>
 * All operations on this type are thread safe.
 *
 * @see Data#customers()
 * @see ReadWriteLocked
 */
public class Customers extends ReadWriteLocked {
    /**
     * Map with {@link Customer#customerId()} as key
     */
    private final Map<Integer, Customer> customers = new HashMap<>();

    /**
     * Adds a new customer and stores it with the given persister.
     *
     * @param customer  the new customer
     * @param persister the persister to store it with
     */
    public void add(Customer customer, Persister persister) {
        write(() -> {
            this.customers.put(customer.customerId(), customer);
            persister.store(this.customers);
        });
    }

    /**
     * Adds a range of new customers and stores it with the given persister.
     *
     * @param customers the new customers
     * @param persister the persister to store them with
     */
    public void addAll(Collection<? extends Customer> customers, Persister persister) {
        write(() -> {
            this.customers.putAll(customers.stream().collect(Collectors.toMap(Customer::customerId, Function.identity())));
            persister.store(this.customers);
        });
    }

    /**
     * Gets the total amount of all customers.
     *
     * @return the amount of customers
     */
    public synchronized int customerCount() {
        return read(customers::size);
    }

    /**
     * Gets all customers as a {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all customers
     */
    public List<Customer> all() {
        return read(() -> new ArrayList<>(customers.values()));
    }

    /**
     * Executes a function with a {@link Stream} of {@link Customer}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T compute(Function<Stream<Customer>, T> streamFunction) {
        return read(() -> streamFunction.apply(customers.values().parallelStream()));
    }

    /**
     * Gets the customer with a specific ID or <code>null</code> if none was found.
     *
     * @param customerId ID to search by
     * @return the matching customer or <code>null</code>
     */
    public Customer ofId(int customerId) {
        return read(() -> customers.get(customerId));
    }

}
