package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;

@Entity
@Table(name = "INVENTORYITEM")
public class InventoryItemEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    private BookEntity book;

    @Column(name = "AMOUNT")
    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHOP_ID")
    private ShopEntity shop;

    public InventoryItemEntity() {
        super();
    }

    public InventoryItemEntity(Long id, BookEntity book, int amount, ShopEntity shop) {
        this.book = book;
        this.amount = amount;
        this.shop = shop;
        setId(id);
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

}
