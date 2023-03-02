package be.rubus.microstream.training.performance.model;

/**
 * Feature type for all named entities, with {@link Comparable} capabilities.
 */
public abstract class Named extends HasId implements Comparable<Named> {
    private final String name;

    protected Named(Long id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Get the name of this entity.
     *
     * @return the name
     */
    public String name() {
        return name;
    }

    @Override
    public int compareTo(Named other) {
        return name().compareTo(other.name());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + name + "]";
    }

}
