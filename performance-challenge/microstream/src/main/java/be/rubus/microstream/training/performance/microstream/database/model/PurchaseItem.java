package be.rubus.microstream.training.performance.microstream.database.model;


import be.rubus.microstream.training.performance.model.Book;
import be.rubus.microstream.training.performance.model.HasId;

import javax.money.MonetaryAmount;

/**
 * Purchase item entity, which holds a {@link Book}, an amount and a price.
 */
public class PurchaseItem extends HasId {
    private final Book book;
    private final int amount;
    private final MonetaryAmount price;

    /**
     * Constructor to create a new {@link PurchaseItem} instance.
     *
     * @param book   not <code>null</code>
     * @param amount positive amount
     */
    public PurchaseItem(Long id, Book book, int amount) {
        super(id);
        this.book = book;
        this.amount = amount;
        this.price = book.retailPrice();
    }

    /**
     * Get the book
     *
     * @return the book
     */
    public Book book() {
        return book;
    }

    /**
     * Get the amount of books
     *
     * @return the amount
     */
    public int amount() {
        return amount;
    }

    /**
     * Get the price the book was sold for
     *
     * @return the price at the time the book was sold
     */
    public MonetaryAmount price() {
        return price;
    }

    /**
     * Computes the total amount of the purchase item (price * amount)
     *
     * @return the total amount of this item
     */
    public MonetaryAmount itemTotal() {
        return price.multiply(amount);
    }

}