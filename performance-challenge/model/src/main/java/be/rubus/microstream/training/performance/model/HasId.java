package be.rubus.microstream.training.performance.model;

import static java.util.Objects.requireNonNull;

public abstract class HasId {

    private final Long id;

    public HasId(Long id) {
        this.id = requireNonNull(id, "id cannot be null");
    }

    public Long getId() {
        return id;
    }
}
