package be.rubus.microstream.training.payara.database;

import be.rubus.microstream.training.payara.model.Product;
import one.microstream.persistence.types.Storer;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

public final class DB {

    private static StorageManager INSTANCE = null;

    private DB() {

    }

    private static StorageManager createStorageManager(Root root) {
        StorageManager result = EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-data")
                .setChannelCount(1)

                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();
        initData(result, root);
        return result;
    }

    private static void initData(StorageManager storageManager, Root root) {
        if (root.getProducts().isEmpty()) {
            root.addProduct(new Product(-1L, "Banana", "A yellow curved fruit", 3));

            // Store entire object graph, unconditionally
            Storer storer = storageManager.createEagerStorer();
            storer.store(root);
            storer.commit();

        }
    }


    public static StorageManager getInstance() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            Root root = new Root();
            INSTANCE = createStorageManager(root);
        }
        return INSTANCE;
    }

    public static Root getRoot() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            Root root = new Root();
            INSTANCE = createStorageManager(root);
        }
        return (Root) INSTANCE.root();
    }
}
