package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PURCHASE", indexes = {@Index(columnList = "EMPLOYEE_ID"), @Index(columnList = "CUSTOMER_ID"), @Index(columnList = "SHOP_ID"), @Index(columnList = "TIME_STAMP")})
public class PurchaseEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerEntity customer;

    @Column(name = "TIME_STAMP")
    private LocalDateTime timestamp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase")
    private List<PurchaseItemEntity> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    private ShopEntity shop;

    public PurchaseEntity() {
        super();
    }

    public PurchaseEntity(Long id, EmployeeEntity employee, CustomerEntity customer, LocalDateTime timestamp, ShopEntity shop) {
        this.employee = employee;
        this.customer = customer;
        this.timestamp = timestamp;
        this.shop = shop;
        setId(id);
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<PurchaseItemEntity> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemEntity> items) {
        this.items = items;
    }

    public PurchaseItemEntity addItem(PurchaseItemEntity item) {
        getItems().add(item);
        item.setPurchase(this);
        return item;
    }

    public PurchaseItemEntity removeItem(PurchaseItemEntity item) {
        getItems().remove(item);
        item.setPurchase(null);
        return item;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

}
