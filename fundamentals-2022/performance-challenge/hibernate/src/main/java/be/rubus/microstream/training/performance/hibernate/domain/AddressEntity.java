package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;


@Entity
@Table(name = "ADDRESS")
public class AddressEntity extends BaseEntity {
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ZIPCODE")
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    private CityEntity city;

    public AddressEntity() {
        super();
    }

    public AddressEntity(Long id, String address, String address2, String zipCode, CityEntity city) {
        this.address = address;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.city = city;
        setId(id);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "AddressEntity [address=" + address + ", address2=" + address2 + ", zipCode=" + zipCode + ", city=" + city + "]";
    }

}
