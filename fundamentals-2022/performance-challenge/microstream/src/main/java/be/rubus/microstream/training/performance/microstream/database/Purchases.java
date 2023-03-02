package be.rubus.microstream.training.performance.microstream.database;

import be.rubus.microstream.training.performance.Range;
import be.rubus.microstream.training.performance.concurrent.ReadWriteLocked;
import be.rubus.microstream.training.performance.microstream.database.model.Purchase;
import be.rubus.microstream.training.performance.microstream.database.model.PurchaseItem;
import be.rubus.microstream.training.performance.microstream.database.model.Shop;
import be.rubus.microstream.training.performance.microstream.utils.CollectionUtils;
import be.rubus.microstream.training.performance.microstream.utils.LazyUtils;
import be.rubus.microstream.training.performance.model.BookSales;
import be.rubus.microstream.training.performance.model.Country;
import be.rubus.microstream.training.performance.model.Customer;
import be.rubus.microstream.training.performance.model.Employee;
import one.microstream.persistence.types.Persister;
import one.microstream.reference.Lazy;
import one.microstream.reference.Referencing;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * All purchases made by all customers in all stores.
 * <p>
 * This type is used to read and write the {@link Purchase}s and statistics thereof.
 * <p>
 * All operations on this type are thread safe.
 *
 * @see Data#purchases()
 * @see ReadWriteLocked
 */
public class Purchases extends ReadWriteLocked {
    /**
     * This class hold all purchases made in a specific year.
     * <p>
     * Note that this class doesn't need to handle concurrency in any way,
     * since it is only used by the Default implementation which handles thread safety.
     */
    private static class YearlyPurchases {
        /*
         * Multiple maps holding references to the purchases, for a faster lookup.
         */
        final Map<Shop, Lazy<List<Purchase>>> shopToPurchases = new HashMap<>(128);
        final Map<Employee, Lazy<List<Purchase>>> employeeToPurchases = new HashMap<>(512);
        final Map<Customer, Lazy<List<Purchase>>> customerToPurchases = new HashMap<>(1024);

        /**
         * Adds a purchase to all collections used by this class.
         *
         * @param purchase the purchase to add
         */
        YearlyPurchases add(Purchase purchase, Persister persister) {
            List<Object> changedObjects = new ArrayList<>();
            addToMap(shopToPurchases, purchase.shop(), purchase, changedObjects);
            addToMap(employeeToPurchases, purchase.employee(), purchase, changedObjects);
            addToMap(customerToPurchases, purchase.customer(), purchase, changedObjects);
            if (persister != null && changedObjects.size() > 0) {
                persister.storeAll(changedObjects);
            }
            return this;
        }

        /**
         * Adds a purchase to a map with a list as values.
         * If no list is present for the given key, it will be created.
         *
         * @param <K>      the key type
         * @param map      the collection
         * @param key      the key
         * @param purchase the purchase to add
         */
        private static <K> void addToMap(Map<K, Lazy<List<Purchase>>> map, K key, Purchase purchase, List<Object> changedObjects) {
            Lazy<List<Purchase>> lazy = map.get(key);
            if (lazy == null) {
                ArrayList<Purchase> list = new ArrayList<>(64);
                list.add(purchase);
                lazy = Lazy.Reference(list);
                map.put(key, lazy);
                changedObjects.add(map);
            } else {
                List<Purchase> list = lazy.get();
                list.add(purchase);
                changedObjects.add(list);
            }
        }

        /**
         * Clears all {@link Lazy} references used by this type
         */
        void clear() {
            clearMap(shopToPurchases);
            clearMap(employeeToPurchases);
            clearMap(customerToPurchases);
        }

        /**
         * Clears all {@link Lazy} references in the given map.
         *
         * @param <K> the key type
         * @param map the map to clear
         */
        private static <K> void clearMap(Map<K, Lazy<List<Purchase>>> map) {
            map.values().forEach(lazy -> LazyUtils.clearIfStored(lazy).ifPresent(List::clear));
        }

        /**
         * @param shop the shop to filter by
         * @return parallel stream with purchases made in a specific shop
         */
        Stream<Purchase> byShop(Shop shop) {
            return CollectionUtils.ensureParallelStream(Lazy.get(shopToPurchases.get(shop)));
        }

        /**
         * @param shopSelector the predicate to filter by
         * @return parallel stream with purchases made in specific shops
         */
        Stream<Purchase> byShops(Predicate<Shop> shopSelector) {
            return shopToPurchases.entrySet().parallelStream().filter(e -> shopSelector.test(e.getKey())).flatMap(e -> CollectionUtils.ensureParallelStream(Lazy.get(e.getValue())));
        }

        /**
         * @param employee the employee to filter by
         * @return parallel stream with purchases made by a specific employee
         */
        Stream<Purchase> byEmployee(Employee employee) {
            return CollectionUtils.ensureParallelStream(Lazy.get(employeeToPurchases.get(employee)));
        }

        /**
         * @param customer the customer to filter by
         * @return parallel stream with purchases made by a specific customer
         */
        Stream<Purchase> byCustomer(Customer customer) {
            return CollectionUtils.ensureParallelStream(Lazy.get(customerToPurchases.get(customer)));
        }

    }


    /**
     * Map with {@link YearlyPurchases}, indexed by the year, of course.
     */
    private final Map<Integer, Lazy<YearlyPurchases>> yearlyPurchases = new ConcurrentHashMap<>(32);

    /**
     * This method is used exclusively by the {@link RandomDataGenerator}.
     */
    public Set<Customer> init(int year, List<Purchase> purchases, Persister persister) {
        return write(() -> {
            YearlyPurchases yearlyPurchases = new YearlyPurchases();
            purchases.forEach(p -> yearlyPurchases.add(p, null));

            Lazy<YearlyPurchases> lazy = Lazy.Reference(yearlyPurchases);
            this.yearlyPurchases.put(year, lazy);

            persister.store(this.yearlyPurchases);

            Set<Customer> customers = new HashSet<>(yearlyPurchases.customerToPurchases.keySet());

            yearlyPurchases.clear();
            lazy.clear();

            return customers;
        });
    }

    /**
     * Gets the range of all years in which purchases were made.
     *
     * @return all years with revenue
     */
    public Range<Integer> years() {
        return read(() -> {
            IntSummaryStatistics summary = yearlyPurchases.keySet().stream().mapToInt(Integer::intValue).summaryStatistics();
            return new Range<>(summary.getMin(), summary.getMax());
        });
    }

    /**
     * Clears all {@link Lazy} references regarding all purchases.
     * This frees the used memory, but you do not lose the persisted data. It is loaded again on demand.
     *
     * @see #clear(int)
     */
    public void clear() {
        List<Integer> years = read(() -> new ArrayList<>(yearlyPurchases.keySet()));
        years.forEach(this::clear);
    }

    /**
     * Clears all {@link Lazy} references regarding purchases of a specific year.
     * This frees the used memory but you do not lose the persisted data. It is loaded again on demand.
     *
     * @param year the year to clear
     * @see #clear()
     */
    public void clear(int year) {

        write(() -> LazyUtils.clearIfStored(
                yearlyPurchases.get(year)).ifPresent(YearlyPurchases::clear));
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Purchase}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param year           year to filter by
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeByYear(final int year, final Function<Stream<Purchase>, T> streamFunction) {
        return read(() -> {
            YearlyPurchases yearlyPurchases = Lazy.get(this.yearlyPurchases.get(year));
            return streamFunction.apply(yearlyPurchases == null ? Stream.empty() : yearlyPurchases.shopToPurchases.values().parallelStream().map(Referencing::get).flatMap(List::stream));
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Purchase}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param shop           shop to filter by
     * @param year           year to filter by
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeByShopAndYear(final Shop shop, final int year, final Function<Stream<Purchase>, T> streamFunction) {
        return read(() -> {
            YearlyPurchases yearlyPurchases = Lazy.get(this.yearlyPurchases.get(year));
            return streamFunction.apply(yearlyPurchases == null ? Stream.empty() : yearlyPurchases.byShop(shop));
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Purchase}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param shopSelector   predicate for shops to filter by
     * @param year           year to filter by
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeByShopsAndYear(final Predicate<Shop> shopSelector, final int year, final Function<Stream<Purchase>, T> streamFunction) {
        return read(() -> {
            YearlyPurchases yearlyPurchases = Lazy.get(this.yearlyPurchases.get(year));
            return streamFunction.apply(yearlyPurchases == null ? Stream.empty() : yearlyPurchases.byShops(shopSelector));
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Purchase}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param employee       employee to filter by
     * @param year           year to filter by
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeByEmployeeAndYear(final Employee employee, final int year, final Function<Stream<Purchase>, T> streamFunction) {
        return read(() -> {
            YearlyPurchases yearlyPurchases = Lazy.get(this.yearlyPurchases.get(year));
            return streamFunction.apply(yearlyPurchases == null ? Stream.empty() : yearlyPurchases.byEmployee(employee));
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Purchase}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param customer       customer to filter by
     * @param year           year to filter by
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeByCustomerAndYear(final Customer customer, final int year, final Function<Stream<Purchase>, T> streamFunction) {
        return read(() -> {
            YearlyPurchases yearlyPurchases = Lazy.get(this.yearlyPurchases.get(year));
            return streamFunction.apply(yearlyPurchases == null ? Stream.empty() : yearlyPurchases.byCustomer(customer));
        });
    }

    /**
     * Computes the best selling books for a specific year.
     *
     * @param year the year to filter by
     * @return list of best selling books
     */
    public List<BookSales> bestSellerList(int year) {
        return computeByYear(year, this::bestSellerList);
    }

    /**
     * Computes the best selling books for a specific year and country.
     *
     * @param year    the year to filter by
     * @param country the country to filter by
     * @return list of best selling books
     */
    public List<BookSales> bestSellerList(int year, Country country) {
        return computeByShopsAndYear(shopInCountryPredicate(country), year, this::bestSellerList);
    }

    private List<BookSales> bestSellerList(Stream<Purchase> purchases) {
        return purchases.flatMap(Purchase::items).collect(groupingBy(PurchaseItem::book, summingInt(PurchaseItem::amount))).entrySet().stream().map(e -> new BookSales(e.getKey(), e.getValue())).sorted().collect(toList());
    }

    /**
     * Counts all purchases which were made by customers in foreign countries.
     *
     * @param year the year to filter by
     * @return the amount of computed purchases
     */
    public long countPurchasesOfForeigners(int year) {
        return computePurchasesOfForeigners(year, Stream::count);
    }

    /**
     * Computes all purchases which were made by customers in foreign cities.
     *
     * @param year the year to filter by
     * @return a list of purchases
     */
    public List<Purchase> purchasesOfForeigners(int year) {
        return computePurchasesOfForeigners(year, purchases -> purchases.collect(toList()));
    }

    private <T> T computePurchasesOfForeigners(int year, Function<Stream<Purchase>, T> streamFunction) {
        return computeByYear(year, purchases -> streamFunction.apply(purchases.filter(purchaseOfForeignerPredicate())));
    }

    /**
     * Computes all purchases which were made by customers in foreign cities.
     *
     * @param year    the year to filter by
     * @param country the country to filter by
     * @return a list of purchases
     */
    public List<Purchase> purchasesOfForeigners(int year, Country country) {
        return computePurchasesOfForeigners(year, country, purchases -> purchases.collect(toList()));
    }

    private <T> T computePurchasesOfForeigners(int year, Country country, Function<Stream<Purchase>, T> streamFunction) {
        return computeByShopsAndYear(shopInCountryPredicate(country), year, purchases -> streamFunction.apply(purchases.filter(purchaseOfForeignerPredicate())));
    }

    private static Predicate<Shop> shopInCountryPredicate(Country country) {
        return shop -> shop.address().city().state().country().code().equals(country.code());
    }

    /**
     * Foreigner predicate customer city is different from shop city.
     *
     * @return Predicate
     */
    private static Predicate<? super Purchase> purchaseOfForeignerPredicate() {
        return p -> p.customer().address().city() != p.shop().address().city();
    }

}
