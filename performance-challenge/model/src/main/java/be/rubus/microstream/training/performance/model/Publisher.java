package be.rubus.microstream.training.performance.model;

/**
 * Publisher entity which holds a name and an {@link Address}.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Publisher extends NamedWithAddress {
    /**
     * Constructor to create a new {@link Publisher} instance.
     *
     * @param name    not empty
     * @param address not <code>null</code>
     */
    public Publisher(Long id, String name, Address address) {
        super(id, name, address);
    }

}
