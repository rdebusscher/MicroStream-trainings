package be.rubus.microstream.training.export;

import be.rubus.microstream.training.export.model.Root;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

public final class StorageManagerFactory {

    private StorageManagerFactory() {
    }

    public static StorageManager createStorageManager(Root root) {
        // requires  microstream-storage-embedded-configuration dependency
        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-data")
                .setChannelCount(2)

                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();
    }
}
