package be.rubus.microstream.training.performance.model.builders;


import be.rubus.microstream.training.performance.model.Address;
import be.rubus.microstream.training.performance.model.Customer;

public class CustomerBuilder {

    private Long id;
    private int customerId;
    private String name;
    private Address address;

    public CustomerBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder withCustomerId(int customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public Customer build() {
        return new Customer(id, customerId, name, address);
    }
}