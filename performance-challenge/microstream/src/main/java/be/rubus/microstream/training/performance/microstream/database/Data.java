package be.rubus.microstream.training.performance.microstream.database;

/**
 * Root object for all data used by this application.
 * <p>
 * This is the entry point for the persisted object graph.
 * <p>
 * The data is divided into four sections:
 * <ul>
 * <li>{@link Books}</li>
 * <li>{@link Shops}</li>
 * <li>{@link Customers}</li>
 * <li>{@link Purchases}</li>
 * </ul>
 *
 * @see <a href="https://manual.docs.microstream.one/data-store/root-instances">MicroStream Reference Manual</a>
 */
public class Data {
    private final Books books = new Books();
    private final Shops shops = new Shops();
    private final Customers customers = new Customers();
    private final Purchases purchases = new Purchases();

    public Data() {
        super();
    }

    /**
     * Get the {@link Books} instance of this data node.
     *
     * @return the {@link Books}
     */
    public Books books() {
        return books;
    }

    /**
     * Get the {@link Shops} instance of this data node.
     *
     * @return the {@link Shops}
     */
    public Shops shops() {
        return shops;
    }

    /**
     * Get the {@link Customers} instance of this data node.
     *
     * @return the {@link Customers}
     */
    public Customers customers() {
        return customers;
    }

    /**
     * Get the {@link Purchases} instance of this data node.
     *
     * @return the {@link Purchases}
     */
    public Purchases purchases() {
        return purchases;
    }


}
