package be.rubus.microstream.training.quickstart.model;

public class PersonBuilder {
    private long id;
    private String name;
    private int age;

    public PersonBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public Person build() {
        Person result = new Person(id);
        result.setName(name);
        result.setAge(age);
        return result;
    }
}