package be.rubus.microstream.training.performance.generator.config;

import be.rubus.microstream.training.performance.generator.data.RandomDataGenerator;

/**
 * Data amount boundaries for the {@link RandomDataGenerator}.
 */
public class RandomDataAmount {
    public static RandomDataAmount valueOf(DataSize dataSize) {
        switch (dataSize) {

            case MEDIUM:
                return Medium();
            case LARGE:
                return Large();
            case HUGE:
                return Huge();

            default:
                throw new IllegalArgumentException("Invalid data amount: " + dataSize);
        }
    }


    public static RandomDataAmount Medium() {
        return new RandomDataAmount(
                0.3, // minRatio
                100, // maxGenres
                3, // maxCountries
                25, // maxPublishersPerCountry
                250, // maxAuthorsPerCountry
                500, // maxBooksPerCountry
                10, // maxCitiesPerCountry
                500, // maxCustomersPerCity
                4, // maxShopsPerCity
                350, // maxBooksPerShop
                15, // maxAgeOfShopsInYears
                7, // maxEmployeesPerShop
                150, // maxPurchasesPerEmployeePerYear
                3 // maxItemsPerPurchase
        );
    }

    public static RandomDataAmount Large() {
        return new RandomDataAmount(
                0.4, // minRatio
                500, // maxGenres
                5, // maxCountries
                50, // maxPublishersPerCountry
                500, // maxAuthorsPerCountry
                1000, // maxBooksPerCountry
                25, // maxCitiesPerCountry
                750, // maxCustomersPerCity
                5, // maxShopsPerCity
                500, // maxBooksPerShop
                20, // maxAgeOfShopsInYears
                10, // maxEmployeesPerShop
                150, // maxPurchasesPerEmployeePerYear
                3 // maxItemsPerPurchase
        );
    }

    public static RandomDataAmount Huge() {
        return new RandomDataAmount(
                0.5, // minRatio
                500, // maxGenres
                5, // maxCountries
                50, // maxPublishersPerCountry
                500, // maxAuthorsPerCountry
                1000, // maxBooksPerCountry
                50, // maxCitiesPerCountry
                750, // maxCustomersPerCity
                5, // maxShopsPerCity
                500, // maxBooksPerShop
                100, // maxAgeOfShopsInYears
                10, // maxEmployeesPerShop
                150, // maxPurchasesPerEmployeePerYear
                3 // maxItemsPerPurchase
        );
    }


    private final double minRatio;
    private final int maxGenres;
    private final int maxCountries;
    private final int maxPublishersPerCountry;
    private final int maxAuthorsPerCountry;
    private final int maxBooksPerCountry;
    private final int maxCitiesPerCountry;
    private final int maxCustomersPerCity;
    private final int maxShopsPerCity;
    private final int maxBooksPerShop;
    private final int maxAgeOfShopsInYears;
    private final int maxEmployeesPerShop;
    private final int maxPurchasesPerEmployeePerYear;
    private final int maxItemsPerPurchase;

    public RandomDataAmount(
            double minRatio,
            int maxGenres,
            int maxCountries,
            int maxPublishersPerCountry,
            int maxAuthorsPerCountry,
            int maxBooksPerCountry,
            int maxCitiesPerCountry,
            int maxCustomersPerCity,
            int maxShopsPerCity,
            int maxBooksPerShop,
            int maxAgeOfShopsInYears,
            int maxEmployeesPerShop,
            int maxPurchasesPerEmployeePerYear,
            int maxItemsPerPurchase
    ) {
        this.minRatio = minRatio;
        this.maxGenres = maxGenres;
        this.maxCountries = maxCountries;
        this.maxPublishersPerCountry = maxPublishersPerCountry;
        this.maxAuthorsPerCountry = maxAuthorsPerCountry;
        this.maxBooksPerCountry = maxBooksPerCountry;
        this.maxCitiesPerCountry = maxCitiesPerCountry;
        this.maxCustomersPerCity = maxCustomersPerCity;
        this.maxShopsPerCity = maxShopsPerCity;
        this.maxBooksPerShop = maxBooksPerShop;
        this.maxAgeOfShopsInYears = maxAgeOfShopsInYears;
        this.maxEmployeesPerShop = maxEmployeesPerShop;
        this.maxPurchasesPerEmployeePerYear = maxPurchasesPerEmployeePerYear;
        this.maxItemsPerPurchase = maxItemsPerPurchase;
    }

    public double minRatio() {
        return minRatio;
    }

    public int maxGenres() {
        return maxGenres;
    }

    public int maxCountries() {
        return maxCountries;
    }

    public int maxPublishersPerCountry() {
        return maxPublishersPerCountry;
    }

    public int maxAuthorsPerCountry() {
        return maxAuthorsPerCountry;
    }

    public int maxBooksPerCountry() {
        return maxBooksPerCountry;
    }

    public int maxCitiesPerCountry() {
        return maxCitiesPerCountry;
    }

    public int maxCustomersPerCity() {
        return maxCustomersPerCity;
    }

    public int maxShopsPerCity() {
        return maxShopsPerCity;
    }

    public int maxBooksPerShop() {
        return maxBooksPerShop;
    }

    public int maxAgeOfShopsInYears() {
        return maxAgeOfShopsInYears;
    }

    public int maxEmployeesPerShop() {
        return maxEmployeesPerShop;
    }

    public int maxPurchasesPerEmployeePerYear() {
        return maxPurchasesPerEmployeePerYear;
    }

    public int maxItemsPerPurchase() {
        return maxItemsPerPurchase;
    }

}
