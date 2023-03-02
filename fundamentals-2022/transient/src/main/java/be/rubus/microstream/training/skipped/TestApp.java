package be.rubus.microstream.training.skipped;

import one.microstream.persistence.types.PersistenceFieldEvaluator;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

import java.lang.reflect.Modifier;
import java.util.List;

public class TestApp {

    public static void main(String[] args) {
        Root root = new Root();
        try (StorageManager storageManager = createStorageManager(root)) {
            ensureDefaultData(storageManager);

            System.out.println(root.getProducts());
        }

    }

    private static void ensureDefaultData(StorageManager storageManager) {
        Root root = (Root) storageManager.root();
        List<Product> products = root.getProducts();
        if (products.isEmpty()) {
            System.out.println("!! No data loaded yet, adding a product");

            Product product = new Product("Ba", "Banana", "This is never stored", "internal");
            products.add(product);
            storageManager.store(products);
        }
    }

    private static StorageManager createStorageManager(Root root) {
        PersistenceFieldEvaluator fieldEvaluator =
                (clazz, field) -> !field.getName().startsWith("_") && !Modifier.isTransient(field.getModifiers());

        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-data")


                .createEmbeddedStorageFoundation()
                .setRoot(root)
                //.onConnectionFoundation(cf -> cf.setFieldEvaluatorPersistable(fieldEvaluator))
                .createEmbeddedStorageManager()
                .start();
    }
}
