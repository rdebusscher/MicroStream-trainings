package be.rubus.microstream.training.quickstart;


import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.types.StorageManager;

public class Config {
    public static StorageManager createStorageManager(Object root) {
        return createStorageFoundation()
                .setRoot(root)
                // Additional programmatic configuration like BinaryHandlers
                .createEmbeddedStorageManager()
                .start();
    }

    public static EmbeddedStorageFoundation<?> createStorageFoundation() {
        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("data")
                // Additional configuration like channel count.
                .createEmbeddedStorageFoundation();
    }
}
