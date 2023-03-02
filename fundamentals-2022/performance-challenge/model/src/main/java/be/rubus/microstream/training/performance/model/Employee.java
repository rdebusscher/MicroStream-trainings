package be.rubus.microstream.training.performance.model;

/**
 * Employee entity which holds a name and an {@link Address}.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Employee extends NamedWithAddress {
    /**
     * Constructor to create a new {@link Employee} instance.
     *
     * @param name    not empty
     * @param address not <code>null</code>
     */
    public Employee(Long id, String name, Address address) {
        super(id, name, address);
    }

}
