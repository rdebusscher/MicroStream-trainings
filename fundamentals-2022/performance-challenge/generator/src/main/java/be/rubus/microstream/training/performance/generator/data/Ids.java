package be.rubus.microstream.training.performance.generator.data;

import java.util.concurrent.atomic.AtomicLong;

public final class Ids {

    private static final AtomicLong countryIds = new AtomicLong(0);
    private static final AtomicLong stateIds = new AtomicLong(0);
    private static final AtomicLong cityIds = new AtomicLong(0);
    private static final AtomicLong addressIds = new AtomicLong(0);

    private static final AtomicLong customerIds = new AtomicLong(0);

    private static final AtomicLong languageIds = new AtomicLong(0);
    private static final AtomicLong bookIds = new AtomicLong(0);
    private static final AtomicLong genreIds = new AtomicLong(0);
    private static final AtomicLong publisherIds = new AtomicLong(0);
    private static final AtomicLong authorIds = new AtomicLong(0);
    private static final AtomicLong shopIds = new AtomicLong(0);
    private static final AtomicLong purchaseItemIds = new AtomicLong(0);
    private static final AtomicLong purchaseIds = new AtomicLong(0);
    private static final AtomicLong employeeIds = new AtomicLong(0);

    private Ids() {
    }

    public static long getNextCountryId() {
        return countryIds.incrementAndGet();
    }

    public static long getNextStateId() {
        return stateIds.incrementAndGet();
    }

    public static long getNextCityId() {
        return cityIds.incrementAndGet();
    }

    public static long getNextAddressId() {
        return addressIds.incrementAndGet();
    }

    public static long getNextCustomerId() {
        return customerIds.incrementAndGet();
    }

    public static long getNextLanguageId() {
        return languageIds.incrementAndGet();
    }

    public static long getNextBookId() {
        return bookIds.incrementAndGet();
    }

    public static long getNextGenreId() {
        return genreIds.incrementAndGet();
    }

    public static long getNextPublisherId() {
        return publisherIds.incrementAndGet();
    }

    public static long getNextAuthorId() {
        return authorIds.incrementAndGet();
    }

    public static long getNextShopId() {
        return shopIds.incrementAndGet();
    }

    public static Long getNextPurchaseItemId() {
        return purchaseItemIds.incrementAndGet();
    }

    public static long getNextPurchaseId() {
        return purchaseIds.incrementAndGet();
    }

    public static long getNextEmployeeId() {
        return employeeIds.incrementAndGet();
    }

}
