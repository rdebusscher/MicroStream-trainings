
package be.rubus.microstream.training.performance.generator.data;

public class DataMetrics {
    private final int bookCount;
    private final int countryCount;
    private final int shopCount;

    public DataMetrics(int bookCount, int countryCount, int shopCount) {
        this.bookCount = bookCount;
        this.countryCount = countryCount;
        this.shopCount = shopCount;
    }

    @Override
    public String toString() {
        return bookCount + " books, "
                + shopCount + " shops in "
                + countryCount + " countries";
    }

}
