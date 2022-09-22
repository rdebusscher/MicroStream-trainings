package be.rubus.microstream.training.performance.model.builders;


import be.rubus.microstream.training.performance.model.Country;

public class CountryBuilder {
    private Long id;
    private String name;
    private String code;

    public CountryBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CountryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CountryBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public Country build() {
        return new Country(id, name, code);
    }
}