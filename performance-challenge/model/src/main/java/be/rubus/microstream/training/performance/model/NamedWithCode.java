package be.rubus.microstream.training.performance.model;

/**
 * Feature type for all named entities with a code.
 */
public abstract class NamedWithCode extends Named {
    private final String code;

    protected NamedWithCode(Long id, String name, String code) {
        super(id, name);

        this.code = code;
    }

    /**
     * Get the code.
     *
     * @return the code
     */
    public String code() {
        return code;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + name() + " - " + code + "]";
    }

}
