package be.rubus.microstream.training.performance.generator.insert;

public class TempInventoryItem {

    private final long shopId;
    private final long bookId;
    private final int amount;

    TempInventoryItem(long shopId, long bookId, int amount) {

        this.shopId = shopId;
        this.bookId = bookId;
        this.amount = amount;
    }

    public long getShopId() {
        return shopId;
    }

    public long getBookId() {
        return bookId;
    }

    public int getAmount() {
        return amount;
    }
}
