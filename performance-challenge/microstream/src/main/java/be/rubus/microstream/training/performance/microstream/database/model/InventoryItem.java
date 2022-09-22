package be.rubus.microstream.training.performance.microstream.database.model;


import be.rubus.microstream.training.performance.model.Book;

/**
 * View of a shop's inventory item slot.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class InventoryItem {
    private final Shop shop;
    private final Book book;
    private final int amount;

    /**
     * Constructor to create a new {@link InventoryItem} instance.
     *
     * @param shop not <code>null</code>
     * @param book not <code>null</code>
     * @param amount positive amount
     */
    public InventoryItem(Shop shop, Book book, int amount) {
        this.shop = shop;
        this.book = book;
        this.amount = amount;
    }

    /**
     * Get the shop which this inventory item belongs to
     *
     * @return the shop
     */
    public Shop shop() {
        return shop;
    }

    /**
     * Get the book of this inventory item
     *
     * @return the book
     */
    public Book book() {
        return book;
    }

    /**
     * Get the amount of this inventory item
     *
     * @return the amount
     */
    public int amount() {
        return amount;
    }

}
