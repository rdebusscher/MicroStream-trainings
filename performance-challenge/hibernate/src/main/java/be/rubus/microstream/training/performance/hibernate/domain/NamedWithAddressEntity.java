package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class NamedWithAddressEntity extends NamedEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    protected NamedWithAddressEntity() {

    }

    protected NamedWithAddressEntity(String name, AddressEntity address) {
        super(name);
        this.address = address;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + this.getName() + " - " + this.address + "]";
    }

}
