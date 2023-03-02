package be.rubus.microstream.training.performance.model;

/**
 * Genre entity which holds a name.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Genre extends Named {
    /**
     * Constructor method to create a new {@link Genre} instance.
     *
     * @param name not empty
     */
    public Genre(Long id, String name) {
        super(id, name);
    }

}
