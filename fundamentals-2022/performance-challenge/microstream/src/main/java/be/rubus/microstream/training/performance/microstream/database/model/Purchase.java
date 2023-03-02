package be.rubus.microstream.training.performance.microstream.database.model;

import be.rubus.microstream.training.performance.model.Customer;
import be.rubus.microstream.training.performance.model.Employee;
import be.rubus.microstream.training.performance.model.HasId;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Purchase entity which holds a {@link Shop}, {@link Employee},
 * {@link Customer}, timestamp and {@link PurchaseItem}s.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Purchase extends HasId {
    private final Shop shop;
    private final Employee employee;
    private final Customer customer;
    private final LocalDateTime timestamp;
    private final List<PurchaseItem> items;
    private transient MonetaryAmount total;

    /**
     * Constructor to create a new {@link Purchase} instance.
     *
     * @param shop      not <code>null</code>
     * @param employee  not <code>null</code>
     * @param customer  not <code>null</code>
     * @param timestamp not <code>null</code>
     * @param items     not empty
     */
    public Purchase(Long id, Shop shop, Employee employee, Customer customer, LocalDateTime timestamp, List<PurchaseItem> items) {
        super(id);
        this.shop = shop;
        this.employee = employee;
        this.customer = customer;
        this.timestamp = timestamp;
        this.items = new ArrayList<>(items);
    }

    /**
     * Get the shop the purchase was made in
     *
     * @return the shop
     */
    public Shop shop() {
        return shop;
    }

    /**
     * Get the employee who sold
     *
     * @return the employee
     */
    public Employee employee() {
        return employee;
    }

    /**
     * Get the customer who made the purchase
     *
     * @return the customer
     */
    public Customer customer() {
        return customer;
    }

    /**
     * The timestamp the purchase was made at
     *
     * @return the timestamp
     */
    public LocalDateTime timestamp() {
        return timestamp;
    }

    /**
     * Get all {@link PurchaseItem}s of this purchase
     *
     * @return a {@link Stream} of {@link PurchaseItem}s
     */
    public Stream<PurchaseItem> items() {
        return items.stream();
    }

    public int itemCount() {
        return items.size();
    }

    /**
     * Computes the total of this purchase (sum of {@link PurchaseItem#itemTotal()})
     *
     * @return the total amount
     */
    public MonetaryAmount total() {
        if (total == null) {
            MonetaryAmount total = null;
            for (PurchaseItem item : items) {
                total = total == null ? item.itemTotal() : total.add(item.itemTotal());
            }
            this.total = total;
        }
        return total;
    }

}
