package be.rubus.microstream.training.config.external;

import be.rubus.microstream.training.config.model.InitData;
import be.rubus.microstream.training.config.model.Root;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

public class TestApp {

    public static void main(String[] args) {
        Root root = new Root();
        try (StorageManager storageManager = createStorageManager(root, "storage.properties" /* "storage.xml"*/)) {

            InitData.ensureDefaultData(storageManager);

            System.out.println(root.getBooks());
            System.out.println("Number of books " + root.getBooks().size());
        }

    }

    private static StorageManager createStorageManager(Root root, String configFile) {
        return EmbeddedStorageConfiguration.load(configFile)
                .createEmbeddedStorageFoundation()
                .setRoot(root)
                .createEmbeddedStorageManager()
                .start();

    }
}
