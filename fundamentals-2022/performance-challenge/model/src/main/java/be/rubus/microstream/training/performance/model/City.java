package be.rubus.microstream.training.performance.model;

import static java.util.Objects.requireNonNull;

/**
 * City entity which holds a name and a {@link State}.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class City extends Named {
    private final State state;

    /**
     * Constructor to create a new {@link City} instance.
     *
     * @param name  not empty
     * @param state not <code>null</code>
     */
    public City(Long id, String name, State state) {
        super(id, name);

        this.state = requireNonNull(state, "State cannot be null");
    }

    /**
     * Get the state.
     *
     * @return the state
     */
    public State state() {
        return state;
    }

}
