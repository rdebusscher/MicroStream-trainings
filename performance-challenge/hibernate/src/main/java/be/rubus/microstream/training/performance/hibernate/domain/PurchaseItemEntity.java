package be.rubus.microstream.training.performance.hibernate.domain;



import be.rubus.microstream.training.performance.hibernate.converter.MoneyConverter;

import javax.money.MonetaryAmount;
import javax.persistence.*;

@Entity
@Table(name = "PURCHASEITEM", indexes = @Index(columnList = "PURCHASE_ID"))
public class PurchaseItemEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    private BookEntity book;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "PRICE")
    @Convert(converter = MoneyConverter.class)
    private MonetaryAmount price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PURCHASE_ID")
    private PurchaseEntity purchase;

    public PurchaseItemEntity() {
        super();
    }

    public PurchaseItemEntity(Long id, BookEntity book, int amount, MonetaryAmount price, PurchaseEntity purchase) {
        this.book = book;
        this.amount = amount;
        this.price = price;
        this.purchase = purchase;
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

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }

}
