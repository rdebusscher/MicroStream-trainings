package be.rubus.microstream.training.performance.model;

/**
 * View of a book's sale numbers, with {@link Comparable} capabilities.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class BookSales implements Comparable<BookSales> {
    private final Book book;
    private final int amount;

    /**
     * Constructor to create a new {@link BookSales} instance.
     *
     * @param book   not <code>null</code>
     * @param amount zero or positive
     */
    public BookSales(Book book, int amount) {
        this.book = book;
        this.amount = amount;
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
     * Get the amount
     *
     * @return the amount
     */
    public int amount() {
        return amount;
    }

    @Override
    public int compareTo(BookSales other) {
        return Integer.compare(other.amount(), this.amount());
    }

    @Override
    public String toString() {
        return "BookSales" + " [book=" + this.book + ", amount=" + this.amount + "]";
    }

}
