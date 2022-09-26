package be.rubus.microstream.training.config.builder;

import be.rubus.microstream.training.config.model.InitData;
import be.rubus.microstream.training.config.model.Root;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

public class TestApp {

    public static void main(String[] args) {
        Root root = new Root();
        try (StorageManager storageManager = createStorageManager(root)) {
            InitData.ensureDefaultData(storageManager);

            System.out.println(root.getBooks());
            System.out.println("Number of books " + root.getBooks().size());
        }

    }

    private static StorageManager createStorageManager(Root root) {
        // requires  microstream-storage-embedded-configuration dependency
        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("builderConfig")
                .setChannelCount(4)

                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();
    }
}
