package be.rubus.microstream.training.performance.model.builders;


import be.rubus.microstream.training.performance.model.City;
import be.rubus.microstream.training.performance.model.State;

public class CityBuilder {

    private Long id;
    private String name;
    private State state;

    public CityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CityBuilder withState(State state) {
        this.state = state;
        return this;
    }

    public City build() {
        return new City(id, name, state);
    }
}