package be.rubus.microstream.training.performance.model;

/**
 * Feature type for all named entities with (optionally) an {@link Address}.
 */
public abstract class NamedWithAddress extends Named {
    private final Address address;

    protected NamedWithAddress(Long id, String name, Address address) {
        super(id, name);

        //this.address = Objects.requireNonNull(address, () -> "Address cannot be null");
        // We cant require this as with JOOQ, the BooksByPrice only load Author and not address of Author (which is not needed)
        this.address = address;
    }

    /**
     * Get the address of this entity.
     *
     * @return the address
     */
    public Address address() {
        return address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + name() + " - " + address + "]";
    }

}
