package be.rubus.microstream.training.performance.model.builders;


import be.rubus.microstream.training.performance.model.Address;
import be.rubus.microstream.training.performance.model.City;

public class AddressBuilder {

    private Long id;
    private String address;
    private String address2;
    private String zipCode;
    private City city;

    public AddressBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public AddressBuilder withAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public AddressBuilder withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public AddressBuilder withCity(City city) {
        this.city = city;
        return this;
    }

    public Address build() {
        return new Address(id, address, address2, zipCode, city);
    }
}