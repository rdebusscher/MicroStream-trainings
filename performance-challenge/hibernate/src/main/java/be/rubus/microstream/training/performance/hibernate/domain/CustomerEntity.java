package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends NamedWithAddressEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<PurchaseEntity> purchases = new ArrayList<>();

    public CustomerEntity() {
        super();
    }

    public CustomerEntity(Long id, String name, AddressEntity address) {
        super(name, address);
        setId(id);
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }

}
