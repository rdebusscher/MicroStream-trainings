package be.rubus.microstream.training.crud.storage;

import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

public final class DB {

    private static StorageManager INSTANCE = null;

    private DB() {

    }

    private static StorageManager createStorageManager(DataRoot root) {
        StorageManager result = EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-data")
                .setChannelCount(1)

                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();

        return result;
    }


    public static StorageManager getInstance() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            DataRoot root = new DataRoot();
            INSTANCE = createStorageManager(root);
        }
        return INSTANCE;
    }

    public static DataRoot getRoot() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            DataRoot root = new DataRoot();
            INSTANCE = createStorageManager(root);
        }
        return (DataRoot) INSTANCE.root();
    }
}
