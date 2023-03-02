package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SHOP")
public class ShopEntity extends NamedWithAddressEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
    private List<EmployeeEntity> employees = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
    private List<PurchaseEntity> purchases = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
    private List<InventoryItemEntity> inventoryItems = new ArrayList<>();

    public ShopEntity() {
        super();
    }

    public ShopEntity(Long id, String name, AddressEntity address) {
        super(name, address);
        setId(id);
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }


    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }


    public List<InventoryItemEntity> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItemEntity> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

}
