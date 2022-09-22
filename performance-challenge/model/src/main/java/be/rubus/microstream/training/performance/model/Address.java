package be.rubus.microstream.training.performance.model;

import static java.util.Objects.requireNonNull;

/**
 * Address entity which holds two address lines, zip code and a {@link City}.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Address extends HasId {
    private final String address;
    private final String address2;
    private final String zipCode;
    private final City city;

    public Address(Long id, String address, String address2, String zipCode, City city) {
        super(id);
        this.address = requireNonNull(address, "Address cannot be null");
        this.address2 = requireNonNull(address2, "Address2 cannot be null");
        this.zipCode = requireNonNull(zipCode, "ZipCode cannot be null");
        this.city = requireNonNull(city, "City cannot be null");
    }

    /**
     * Get the first address line.
     *
     * @return first address line
     */
    public String address() {
        return address;
    }

    /**
     * Get the second address line.
     *
     * @return second address line
     */
    public String address2() {
        return address2;
    }

    /**
     * Get the zip code.
     *
     * @return zip code
     */
    public String zipCode() {
        return zipCode;
    }

    /**
     * Get the city.
     *
     * @return city
     */
    public City city() {
        return city;
    }

    @Override
    public String toString() {
        return "Address" + " [address=" + address + ", address2=" + address2 + ", zipCode=" + zipCode + ", city=" + city + "]";
    }

}
