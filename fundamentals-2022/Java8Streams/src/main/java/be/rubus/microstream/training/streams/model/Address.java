package be.rubus.microstream.training.streams.model;

import java.util.StringJoiner;

public class Address {
    private String id;
    private String street;
    private String zip;
    private String city;
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]\n")
                .add("id='" + id + "'")
                .add("street='" + street + "'")
                .add("zip='" + zip + "'")
                .add("city='" + city + "'")
                .add("country='" + country + "'")
                .toString();
    }
}
