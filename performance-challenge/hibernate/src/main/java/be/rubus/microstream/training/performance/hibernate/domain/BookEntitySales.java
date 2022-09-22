package be.rubus.microstream.training.performance.hibernate.domain;

/**
 * View of a book's sale numbers, with {@link Comparable} capabilities.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class BookEntitySales implements Comparable<BookEntitySales> {
    private final BookEntity book;
    private final long amount;

    /**
     * Constructor to create a new {@link BookEntitySales} instance.
     *
     * @param book   not <code>null</code>
     * @param amount zero or positive
     */
    public BookEntitySales(BookEntity book, long amount) {
        this.book = book;
        this.amount = amount;
    }

    /**
     * Get the book
     *
     * @return the book
     */
    public BookEntity book() {
        return book;
    }

    /**
     * Get the amount
     *
     * @return the amount
     */
    public long amount() {
        return amount;
    }

    @Override
    public int compareTo(BookEntitySales other) {
        return Long.compare(other.amount(), this.amount());
    }

    @Override
    public String toString() {
        return "BookEntitySales" + " [book=" + this.book + ", amount=" + this.amount + "]";
    }

}
