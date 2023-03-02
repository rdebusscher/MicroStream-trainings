package be.rubus.microstream.training.performance.generator.insert;

import be.rubus.microstream.training.performance.Range;
import be.rubus.microstream.training.performance.generator.GenerateData;
import be.rubus.microstream.training.performance.hibernate.domain.*;
import be.rubus.microstream.training.performance.hibernate.util.HibernateUtil;
import be.rubus.microstream.training.performance.microstream.StorageManagerFactory;
import be.rubus.microstream.training.performance.microstream.database.Data;
import be.rubus.microstream.training.performance.microstream.database.model.Purchase;
import be.rubus.microstream.training.performance.microstream.database.model.PurchaseItem;
import be.rubus.microstream.training.performance.microstream.database.model.Shop;
import be.rubus.microstream.training.performance.model.*;
import be.rubus.microstream.training.performance.utils.ChannelUtil;
import one.microstream.storage.types.StorageManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UploadIntoDatabase {
    private static Logger logger;

    private static final AutoIncrement INVENTORY_ITEM_ID = new AutoIncrement();

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(GenerateData.class);

        Data root = new Data();

        try (StorageManager storageManager =
                     StorageManagerFactory.create("bookstore", ChannelUtil.channelCount(), root)) {

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {

                migrateAddresses(session, root);
                migrateCustomers(session, root);
                migrateBooks(session, root);
                migrateShops(session, root);

                // This is slow due to the indexes on the purchase and purchase_item table.
                migratePurchases(session, root);
            }

        }
    }

    private static void migrateAddresses(Session session, Data data) {
        logger.info("> addresses");


        List<Address> addresses = StreamUtil.concat(
                        data.books().computeAuthors(authors -> authors.map(Author::address)),
                        data.books().computePublishers(publishers -> publishers.map(Publisher::address)),
                        data.shops().compute(shops -> shops.flatMap(shop -> StreamUtil.concat(
                                Stream.of(shop.address()),
                                shop.employees().map(Employee::address)
                        ))),
                        data.customers().compute(customers -> customers.map(Customer::address))
                )
                .distinct()
                .collect(Collectors.toList());

        List<City> cities = addresses.stream()
                .map(Address::city)
                .distinct()
                .collect(Collectors.toList());

        List<State> states = cities.stream()
                .map(City::state)
                .distinct()
                .collect(Collectors.toList());

        List<Country> countries = states.stream()
                .map(State::country)
                .distinct()
                .collect(Collectors.toList());

        Transaction transaction = session.beginTransaction();

        store(session, countries, UploadIntoDatabase::newCountryEntity);

        store(session, states, UploadIntoDatabase::newStateEntity);
        store(session, cities, UploadIntoDatabase::newCityEntity);
        store(session, addresses, UploadIntoDatabase::newAddressEntity);

        transaction.commit();

        countries.clear();
        states.clear();
        cities.clear();

        logger.info("+ " + addresses.size() + " addresses");
    }

    private static void migrateCustomers(Session session, Data data) {
        logger.info("> customers");

        List<Customer> customers = data.customers().all();

        //references.addReferences(Customer.class, customers);

        Transaction transaction = session.beginTransaction();

        store(session, customers, UploadIntoDatabase::newCustomerEntity);
        transaction.commit();

        logger.info("+ " + customers.size() + " customers");

    }

    private static void migrateBooks(Session session, Data data) {
        logger.info("> books");

        List<Genre> genres = data.books()
                .computeGenres(stream -> stream.collect(Collectors.toList()));

        List<Author> authors = data.books()
                .computeAuthors(stream -> stream.collect(Collectors.toList()));

        List<Publisher> publishers = data.books()
                .computePublishers(stream -> stream.collect(Collectors.toList()));

        List<Language> languages = data.books()
                .computeLanguages(stream -> stream.collect(Collectors.toList()));

        List<Book> books = data.books().all();


        Transaction transaction = session.beginTransaction();

        store(session, genres, UploadIntoDatabase::newGenreEntity);
        store(session, authors, UploadIntoDatabase::newAuthorEntity);
        store(session, publishers, UploadIntoDatabase::newPublisherEntity);
        store(session, languages, UploadIntoDatabase::newLanguageEntity);
        store(session, books, UploadIntoDatabase::newBookEntity);

        transaction.commit();

        authors.clear();
        genres.clear();
        languages.clear();
        publishers.clear();

        logger.info("+ " + books.size() + " books");
    }

    private static void migrateShops(Session session, Data data) {
        logger.info("> shops");

        List<Shop> shops = data.shops().all();

        List<EmployeeWithShop> employees = shops.stream().flatMap(shop -> shop.employees().map(emp -> new EmployeeWithShop(emp, shop))).collect(Collectors.toList());

        List<TempInventoryItem> inventoryItems = shops.stream()
                .flatMap(shop -> shop.inventory().compute(stream -> stream.map(slot -> {
                     Book book = slot.getKey();
                     int amount = slot.getValue();
                    return new TempInventoryItem(shop.getId(), book.getId(), amount);
                })))
                .collect(Collectors.toList());

        Transaction transaction = session.beginTransaction();

        store(session, shops, UploadIntoDatabase::newShopEntity);
        store(session, employees, UploadIntoDatabase::newEmployeeEntity);

        store(session, inventoryItems, UploadIntoDatabase::newInventoryItemEntity);

        transaction.commit();

        logger.info("+ " + shops.size() + " shops");

    }

    private static void migratePurchases(Session session, Data data) {

        Range<Integer> years = data.purchases().years();
        IntStream.rangeClosed(years.getLowerBound(), years.getUpperbound()).forEach(year ->
        {
            logger.info("> purchases in " + year);

            List<Purchase> purchases = data.purchases().computeByYear(
                    year,
                    stream -> stream.collect(Collectors.toList())
            );

            EntityIdMap<ForeignIdItem<PurchaseItem>> purchaseItems = purchases.stream()
                    .flatMap(purchase -> {
                         Long pid = purchase.getId();
                        return purchase.items().map(item -> new ForeignIdItem<>(item, pid));
                    })
                    .collect(StreamUtil.toEntityIdMap());

            Transaction transaction = session.beginTransaction();

            store(session, purchases, UploadIntoDatabase::newPurchaseEntity);
            store(session, purchaseItems, UploadIntoDatabase::newPurchaseItemEntity);

            transaction.commit();

            logger.info("+ " + purchases.size() + " purchases in " + year);

            purchaseItems.clear();
            data.purchases().clear(year);  // Clear up Lazy reference.
            purchases.clear();

        });

    }


    private static StateEntity newStateEntity(State state) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(state.country().getId());
        return new StateEntity(state.getId(), state.name(), countryEntity);
    }

    private static CountryEntity newCountryEntity(Country country) {
        CountryEntity countryEntity = new CountryEntity(country.name(), country.code());
        countryEntity.setId(country.getId());
        return countryEntity;
    }

    private static CityEntity newCityEntity(City city) {
        StateEntity stateEntity = new StateEntity();
        stateEntity.setId(city.state().getId());
        return new CityEntity(city.getId(), city.name(), stateEntity);
    }

    private static AddressEntity newAddressEntity(Address address) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setId(address.city().getId());
        return new AddressEntity(address.getId(), address.address(), address.address2(), address.zipCode(), cityEntity);
    }

    private static CustomerEntity newCustomerEntity(Customer customer) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(customer.address().getId());
        return new CustomerEntity(customer.getId(), customer.name(), addressEntity);
    }

    private static GenreEntity newGenreEntity(Genre genre) {
        GenreEntity genreEntity = new GenreEntity(genre.name());
        genreEntity.setId(genre.getId());
        return genreEntity;
    }

    private static AuthorEntity newAuthorEntity(Author author) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(author.address().getId());
        return new AuthorEntity(author.getId(), author.name(), addressEntity);
    }

    private static PublisherEntity newPublisherEntity(Publisher publisher) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(publisher.address().getId());
        return new PublisherEntity(publisher.getId(), publisher.name(), addressEntity);
    }

    private static LanguageEntity newLanguageEntity(Language language) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(language.getId());
        languageEntity.setLanguageTag(language.locale().getLanguage());
        return languageEntity;
    }

    private static BookEntity newBookEntity(Book book) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(book.author().getId());

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(book.genre().getId());

        PublisherEntity publisherEntity = new PublisherEntity();
        publisherEntity.setId(book.publisher().getId());


        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(book.language().getId());

        return new BookEntity(book.getId(), book.isbn13(), book.title(), authorEntity, genreEntity, publisherEntity,
                book.purchasePrice(), book.retailPrice(), languageEntity);
    }

    private static ShopEntity newShopEntity(Shop shop) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(shop.address().getId());
        return new ShopEntity(shop.getId(), shop.name(), addressEntity);
    }


    private static EmployeeEntity newEmployeeEntity(EmployeeWithShop employeeWithShop) {
        AddressEntity addressEntity = new AddressEntity();
        Employee employee = employeeWithShop.getEmployee();
        addressEntity.setId(employee.address().getId());
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(employeeWithShop.getShop().getId());
        return new EmployeeEntity(employee.getId(), employee.name(), addressEntity, shopEntity);
    }

    private static InventoryItemEntity newInventoryItemEntity(TempInventoryItem tempInventoryItem) {

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(tempInventoryItem.getBookId());

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(tempInventoryItem.getShopId());
        return new InventoryItemEntity(INVENTORY_ITEM_ID.next(), bookEntity, tempInventoryItem.getAmount(), shopEntity);
    }

    private static PurchaseEntity newPurchaseEntity(Purchase purchase) {

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(purchase.employee().getId());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(purchase.customer().getId());

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(purchase.shop().getId());

        return new PurchaseEntity(purchase.getId(), employeeEntity, customerEntity, purchase.timestamp(), shopEntity);
    }

    private static PurchaseItemEntity newPurchaseItemEntity(ForeignIdItem<PurchaseItem> purchaseItem) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(purchaseItem.item.book().getId());

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseItem.foreignId);

        return new PurchaseItemEntity(purchaseItem.item.getId(), bookEntity, purchaseItem.item.amount(), purchaseItem.item.price(), purchaseEntity);
    }

    private static <P, U> void store(Session session, List<P> entities, EntityCreator<P, U> entityCreator) {
        entities.stream().map(entityCreator::createEntity).forEach(session::save);
    }

    private static <P, U> void store(Session session, EntityIdMap<P> entities, EntityCreator<P, U> entityCreator) {
        entities.keySet().stream().map(entityCreator::createEntity).forEach(session::save);
    }

}
