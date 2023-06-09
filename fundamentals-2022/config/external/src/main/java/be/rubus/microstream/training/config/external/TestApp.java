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

                // In environments with multi level class loaders (Application runtimes, ...)
                //.onConnectionFoundation(cf -> cf.setClassLoaderProvider(ClassLoaderProvider.New(
                //    Thread.currentThread().getContextClassLoader())))

                // Specialised Handlers
                //.onConnectionFoundation(BinaryHandlersJDK17::registerJDK17TypeHandlers);

                .setRoot(root)
                .createEmbeddedStorageManager()
                .start();

    }
}
