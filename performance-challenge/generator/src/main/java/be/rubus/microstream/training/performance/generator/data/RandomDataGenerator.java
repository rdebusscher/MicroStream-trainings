package be.rubus.microstream.training.performance.generator.data;

import be.rubus.microstream.training.performance.generator.config.DataSize;
import be.rubus.microstream.training.performance.generator.config.RandomDataAmount;
import be.rubus.microstream.training.performance.microstream.database.Books;
import be.rubus.microstream.training.performance.microstream.database.Customers;
import be.rubus.microstream.training.performance.microstream.database.Purchases;
import be.rubus.microstream.training.performance.microstream.database.Shops;
import be.rubus.microstream.training.performance.microstream.database.model.Purchase;
import be.rubus.microstream.training.performance.microstream.database.model.PurchaseItem;
import be.rubus.microstream.training.performance.microstream.database.model.Shop;
import be.rubus.microstream.training.performance.model.*;
import be.rubus.microstream.training.performance.model.builders.*;
import be.rubus.microstream.training.performance.utils.MoneyUtil;
import com.github.javafaker.Faker;
import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Random data generator for the {@code BookStoreDemo}'s Data root.
 * <p>
 * Data amount boundaries can be controlled with {@link DataSize}.
 */
public class RandomDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDataGenerator.class);

    private static class CountryData extends ArrayList<City> {
        private Faker faker;
        private Locale locale;
        private List<Shop> shops = new ArrayList<>();
        private Map<City, List<Customer>> people;

        CountryData(Faker faker, Locale locale) {
            super(512);

            this.faker = faker;
            this.locale = locale;
        }

        City randomCity(Random random) {
            return get(random.nextInt(size()));
        }

        Customer randomCustomer(Random random) {
            return randomCustomer(random, randomCity(random));
        }

        Customer randomCustomer(Random random, City city) {
            List<Customer> peopleOfCity = people.get(city);
            return peopleOfCity.get(random.nextInt(peopleOfCity.size()));
        }

        void dispose() {
            faker = null;
            locale = null;

            shops.clear();
            shops = null;

            people.values().forEach(List::clear);
            people.clear();

            clear();
        }
    }

    private final Books books;
    private final Shops shops;
    private final Customers customers;
    private final Purchases purchases;
    private final RandomDataAmount dataAmount;
    private final StorageManager storageManager;

    private final Random random = new Random();
    private final Faker faker = Faker.instance();
    private final AtomicInteger customerId = new AtomicInteger(0);  // FIXME Needed? Ids.customerIds?
    private final Set<String> usedIsbns = new HashSet<>(4096);
    private final List<Book> bookList = new ArrayList<>(4096);

    private final BigDecimal minPrice = new BigDecimal(5);
    private final BigDecimal maxPrice = new BigDecimal(25);
    private final BigDecimal priceRange = maxPrice.subtract(minPrice);

    public RandomDataGenerator(Books books, Shops shops, Customers customers, Purchases purchases, DataSize dataSize, StorageManager storageManager) {
        this.books = books;
        this.shops = shops;
        this.customers = customers;
        this.purchases = purchases;
        this.dataAmount = RandomDataAmount.valueOf(dataSize);
        this.storageManager = storageManager;
    }

    public DataMetrics generate() {
        List<Locale> locales = supportedLocales();

        LOGGER.info("+ " + locales.size() + " locales");

        List<CountryData> countries = locales.parallelStream()
                .map(this::createCountry)
                .collect(toList());

        createBooks(countries);

        createShops(countries);

        createPurchases(countries);

        DataMetrics metrics = new DataMetrics(
                books.bookCount(),
                countries.size(),
                shops.shopCount()
        );

        shops.clear();
        usedIsbns.clear();
        bookList.clear();
        countries.forEach(CountryData::dispose);
        countries.clear();

        System.gc();

        return metrics;
    }

    private List<Locale> supportedLocales() {
        List<Locale> locales = Arrays.asList(
                Locale.US,
                Locale.CANADA_FRENCH,
                Locale.GERMANY,
                Locale.FRANCE,
                Locale.UK,
                new Locale("pt", "BR"),
                new Locale("de", "AT"),
                new Locale("fr", "CH"),
                new Locale("nl", "NL"),
                new Locale("hu", "HU"),
                new Locale("pl", "PL")
        );

        int maxCountries = dataAmount.maxCountries();
        int max = maxCountries == -1
                ? locales.size()
                : maxCountries;
        return max >= locales.size()
                ? locales
                : locales.subList(0, max);
    }

    private CountryData createCountry(Locale locale) {
        LOGGER.info("> country " + locale.getDisplayCountry());

        Faker faker = Faker.instance(locale);
        Set<String> cityNameSet = new HashSet<>();
        Map<String, State> stateMap = new HashMap<>();
        Country country = new CountryBuilder()
                .withId(Ids.getNextCountryId())
                .withName(locale.getDisplayCountry(Locale.ENGLISH))
                .withCode(locale.getCountry())
                .build();
        CountryData countryData = new CountryData(faker, locale);

        randomRange(dataAmount.maxCitiesPerCountry()).forEach(i -> {
            com.github.javafaker.Address fakerAddress = faker.address();
            String cityName = fakerAddress.city();
            if (cityNameSet.add(cityName)) {
                String stateName = fakerAddress.state();
                State state = stateMap.computeIfAbsent(
                        stateName,
                        s -> new StateBuilder()
                                .withId(Ids.getNextStateId())
                                .withName(stateName)
                                .withCountry(country)
                                .build()
                );
                countryData.add(new CityBuilder()
                        .withId(Ids.getNextCityId())
                        .withName(cityName)
                        .withState(state)
                        .build());
            }
        });

        countryData.people = new HashMap<>(countryData.size(), 1.0f);
        countryData.parallelStream().forEach(city -> countryData.people.put(city, createCustomers(countryData, city)));

        LOGGER.info(
                "+ country " + locale.getDisplayCountry() + " [" + countryData.size() + " cities, " +
                        countryData.people.values().stream().mapToInt(List::size).sum() + " customers] "
        );

        return countryData;
    }

    private List<Customer> createCustomers(CountryData countryData, City city) {
        return randomRange(dataAmount.maxCustomersPerCity())
                .mapToObj(i -> new CustomerBuilder()
                        .withId(Ids.getNextCustomerId())
                        .withCustomerId(customerId.incrementAndGet())
                        .withName(countryData.faker.name().fullName())
                        .withAddress(createAddress(city, countryData.faker))
                        .build())
                .collect(toList());
    }

    private void createBooks(List<CountryData> countries) {
        List<Genre> genres = createGenres();
        countries.parallelStream().forEach(country -> {
            LOGGER.info("> books in " + country.locale.getDisplayCountry());

            List<Publisher> publishers = createPublishers(country);
            List<Author> authors = createAuthors(country);
            Language language = new Language(Ids.getNextLanguageId(), country.locale);
            List<Book> books = IntStream.range(0, dataAmount.maxBooksPerCountry())
                    .mapToObj(i -> country.faker.book().title())
                    .map(title -> createBook(genres, publishers, authors, language, title))
                    .collect(toList());

            bookList.addAll(books);

            LOGGER.info("+ " + books.size() + " books in " + country.locale.getDisplayCountry());
        });

        books.addAll(bookList, storageManager);
    }

    private Book createBook(List<Genre> genres, List<Publisher> publishers, List<Author> authors, Language language, String title) {
        String isbn;
        synchronized (usedIsbns) {
            while (!usedIsbns.add(isbn = faker.code().isbn13(true))) {
                // empty loop
            }
        }
        Genre genre = genres.get(random.nextInt(genres.size()));
        Publisher publisher = publishers.get(random.nextInt(publishers.size()));
        Author author = authors.get(random.nextInt(authors.size()));
        MonetaryAmount purchasePrice = MoneyUtil.money(randomPurchasePrice());
        MonetaryAmount retailPrice = MoneyUtil.retailPrice(purchasePrice);
        return new BookBuilder().withId(Ids.getNextBookId()).withIsbn13(isbn).withTitle(title).withAuthor(author).withGenre(genre).withPublisher(publisher).withLanguage(language).withPurchasePrice(purchasePrice).withRetailPrice(retailPrice).build();
    }

    private List<Genre> createGenres() {
        return randomRange(dataAmount.maxGenres())
                .mapToObj(i -> faker.book().genre())
                .distinct()
                .map(name -> new Genre(Ids.getNextGenreId(), name))
                .collect(toList());
    }

    private List<Publisher> createPublishers(CountryData countryData) {
        return randomRange(dataAmount.maxPublishersPerCountry())
                .mapToObj(i -> countryData.faker.book().publisher())
                .distinct()
                .map(name -> new Publisher(Ids.getNextPublisherId(), name, createAddress(countryData.randomCity(random), countryData.faker)))
                .collect(toList());
    }

    private List<Author> createAuthors(CountryData countryData) {
        return randomRange(dataAmount.maxAuthorsPerCountry())
                .mapToObj(i -> countryData.faker.book().author())
                .distinct()
                .map(name -> new AuthorBuilder()
                        .withId(Ids.getNextAuthorId())
                        .withName(name)
                        .withAddress(createAddress(countryData.randomCity(random), countryData.faker))
                        .build())
                .collect(toList());
    }

    private void createShops(List<CountryData> countries) {
        countries.parallelStream().forEach(country -> {
            LOGGER.info("> shops in " + country.locale.getDisplayCountry());

            country.forEach(
                    city -> randomRange(dataAmount.maxShopsPerCity()).forEach(
                            i -> country.shops.add(createShop(country, city, i))
                    )
            );

            LOGGER.info("+ " + country.shops.size() + " shops in " + country.locale.getDisplayCountry());
        });

        countries.forEach(country -> shops.addAll(country.shops, storageManager));
    }

    private Shop createShop(CountryData countryData, City city, int nr) {
        String name = city.name() + " Shop " + nr;
        Address address = createAddress(city, countryData.faker);
        List<Employee> employees = createEmployees(countryData, city);
        Map<Book, Integer> inventory = randomRange(dataAmount.maxBooksPerShop())
                .mapToObj(i -> randomBook())
                .distinct()
                .collect(toMap(
                        book -> book,
                        book -> random.nextInt(50) + 1
                ));
        return new Shop(Ids.getNextShopId(), name, address, employees, new Inventory(inventory));
    }

    private void createPurchases(List<CountryData> countries) {
        Set<Customer> customerList = new HashSet<>(4096);

        int thisYear = Year.now().getValue();
        int startYear = thisYear - randomMax(dataAmount.maxAgeOfShopsInYears()) + 1;
        IntStream.rangeClosed(startYear, thisYear).forEach(
                year -> createPurchases(countries, year, customerList)
        );

        customers.addAll(customerList, storageManager);
        customerList.clear();
    }

    private void createPurchases(List<CountryData> countries, int year, Set<Customer> customers) {
        LOGGER.info("> purchases in " + year);

        List<Purchase> purchaseList = countries.parallelStream()
                .flatMap(
                        country -> country.shops.stream().flatMap(
                                shop -> createPurchases(country, year, shop)
                        )
                )
                .collect(toList());

        Set<Customer> customersForYear = purchases.init(year, purchaseList, storageManager);

        LOGGER.info("+ " + purchaseList.size() + " purchases in " + year);

        customers.addAll(customersForYear);

        customersForYear.clear();
        purchaseList.clear();

        System.gc();
    }

    private Stream<Purchase> createPurchases(CountryData countryData, int year, Shop shop) {
        List<Book> books = shop.inventory().books();
        boolean isLeapYear = Year.of(year).isLeap();

        return shop.employees().flatMap(employee ->
                randomRange(dataAmount.maxPurchasesPerEmployeePerYear()).mapToObj(pi -> {
                    Customer customer = pi % 10 == 0
                            ? countryData.randomCustomer(random)
                            : countryData.randomCustomer(random, shop.address().city());
                    LocalDateTime timestamp = randomDateTime(year, isLeapYear, random);
                    List<PurchaseItem> items = randomRange(dataAmount.maxItemsPerPurchase())
                            .mapToObj(ii -> new PurchaseItem(Ids.getNextPurchaseItemId(), books.get(random.nextInt(books.size())), random.nextInt(3) + 1))
                            .collect(toList());
                    return new Purchase(Ids.getNextPurchaseId(), shop, employee, customer, timestamp, items);
                })
        );
    }

    private LocalDateTime randomDateTime(int year, boolean isLeapYear, Random random) {
        Month month = Month.of(random.nextInt(12) + 1);
        int dayOfMonth = random.nextInt(month.length(isLeapYear)) + 1;
        int hour = 8 + random.nextInt(11);
        int minute = random.nextInt(60);
        return LocalDateTime.of(year, month.getValue(), dayOfMonth, hour, minute);
    }

    private Book randomBook() {
        return bookList.get(random.nextInt(bookList.size()));
    }

    private List<Employee> createEmployees(CountryData countryData, City city) {
        return randomRange(dataAmount.maxEmployeesPerShop())
                .mapToObj(i -> new Employee(Ids.getNextEmployeeId(),
                        countryData.faker.name().fullName(),
                        createAddress(city, countryData.faker)
                ))
                .collect(toList());
    }

    private Address createAddress(City city, Faker faker) {
        com.github.javafaker.Address fa = faker.address();
        return new AddressBuilder()
                .withId(Ids.getNextAddressId())
                .withAddress(fa.streetAddress())
                .withAddress2(fa.secondaryAddress())
                .withZipCode(fa.zipCode())
                .withCity(city)
                .build();
    }

    private BigDecimal randomPurchasePrice() {
        return minPrice
                .add(BigDecimal.valueOf(Math.random()).multiply(priceRange));
    }

    private IntStream randomRange(int upperBoundInclusive) {
        return IntStream.rangeClosed(0, randomMax(upperBoundInclusive));
    }

    private int randomMax(int upperBoundInclusive) {
        int max = random.nextInt(upperBoundInclusive);
        return Math.max(max, (int) (upperBoundInclusive * dataAmount.minRatio()));

    }

}
