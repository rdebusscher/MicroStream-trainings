package be.rubus.microstream.training.quickstart.lazy;


import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.types.StorageManager;

public class Config {
    public static StorageManager createStorageManager(Object root, String targetDirectory) {
        return createStorageFoundation(targetDirectory)
                .setRoot(root)
                // Additional programmatic configuration like BinaryHandlers
                .createEmbeddedStorageManager()
                .start();
    }

    public static EmbeddedStorageFoundation<?> createStorageFoundation(String targetDirectory) {
        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory(targetDirectory)
                // Additional configuration like channel count.
                .createEmbeddedStorageFoundation();
    }
}
