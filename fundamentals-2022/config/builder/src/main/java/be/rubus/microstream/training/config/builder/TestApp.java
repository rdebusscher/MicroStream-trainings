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
                // Further customise the Foundation if needed.

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
