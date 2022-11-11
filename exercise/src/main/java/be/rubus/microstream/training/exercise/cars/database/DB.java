package be.rubus.microstream.training.exercise.cars.database;

import be.rubus.microstream.training.exercise.cars.model.Brand;
import be.rubus.microstream.training.exercise.cars.model.Car;
import be.rubus.microstream.training.exercise.cars.model.Company;
import one.microstream.persistence.types.Storer;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

public final class DB {

    private static StorageManager INSTANCE = null;

    private DB() {

    }

    private static StorageManager createStorageManager(Company root) {
        StorageManager result = EmbeddedStorageConfiguration.load("microstream.properties")
                .createEmbeddedStorageFoundation()
                .setRoot(root)
                .createEmbeddedStorageManager()
                .start();
        initData(result, root);
        return result;
    }

    private static void initData(StorageManager storageManager, Company root) {
        if (root.getName() == null) {
            root.setName("Rudy's Car Rental");
            Brand bmw = Brand.newBrand("BMW");
            Brand mercedes = Brand.newBrand("Mercedes");
            Brand opel = Brand.newBrand("Opel");

            root.getBrands().add(bmw);
            root.getBrands().add(mercedes);
            root.getBrands().add(opel);

            Car car = new Car(0L);
            car.setBrand(opel);
            car.setModel("Zafira");
            car.setPrice(84.9);

            root.getCars().add(car);

            // Store entire object graph, unconditionally
            Storer storer = storageManager.createEagerStorer();
            storer.store(root);
            storer.commit();
        }
    }


    public static StorageManager getInstance() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            Company root = new Company();
            INSTANCE = createStorageManager(root);
        }
        return INSTANCE;
    }

    public static Company getRoot() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            Company root = new Company();
            INSTANCE = createStorageManager(root);
        }
        return (Company) INSTANCE.root();
    }
}
