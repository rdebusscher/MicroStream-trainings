package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends NamedWithAddressEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<PurchaseEntity> purchases = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHOP_ID")
    private ShopEntity shop;

    public EmployeeEntity() {
        super();
    }

    public EmployeeEntity(Long id, String name, AddressEntity address, ShopEntity shop) {
        super(name, address);
        this.shop = shop;
        setId(id);
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }


    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

}
