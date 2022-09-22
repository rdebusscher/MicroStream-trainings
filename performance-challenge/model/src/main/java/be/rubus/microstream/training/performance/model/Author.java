package be.rubus.microstream.training.performance.model;

/**
 * Author entity which holds a name and an {@link Address}.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Author extends NamedWithAddress {
    /**
     * Constructor to create a new {@link Author} instance.
     *
     * @param name    not empty
     * @param address not <code>null</code>
     */
    public Author(Long id, String name, Address address) {
        super(id, name, address);
    }

}
