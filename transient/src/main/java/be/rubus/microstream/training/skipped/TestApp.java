package be.rubus.microstream.training.skipped;

import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

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

            Product product = new Product("Ba", "Banana", "This is never stored");
            products.add(product);
            storageManager.store(products);
        }
    }

    private static StorageManager createStorageManager(Root root) {

        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-data")


                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();
    }
}
