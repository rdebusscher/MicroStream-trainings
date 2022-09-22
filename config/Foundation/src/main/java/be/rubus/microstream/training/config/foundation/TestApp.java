package be.rubus.microstream.training.config.foundation;

import be.rubus.microstream.training.config.model.InitData;
import be.rubus.microstream.training.config.model.Root;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.reflect.ClassLoaderProvider;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.types.*;

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
        // Doesn't need the microstream-storage-embedded-configuration dependency
        NioFileSystem fileSystem = NioFileSystem.New();

        EmbeddedStorageFoundation<?> storageFoundation = EmbeddedStorageFoundation.New()
                .setConfiguration(
                        StorageConfiguration.Builder()
                                .setStorageFileProvider(
                                        Storage.FileProviderBuilder(fileSystem)
                                                .setDirectory(fileSystem.ensureDirectoryPath("foundationConfig"))
                                                .createFileProvider()
                                )
                                .setChannelCountProvider(StorageChannelCountProvider.New(8))
                                .setBackupSetup(StorageBackupSetup.New(
                                        fileSystem.ensureDirectoryPath("foundation-backup")
                                ))
                                .createConfiguration()
                )
                .setRoot(root);

        // In environments with multi level class loaders (Application runtimes, ...)
        storageFoundation.onConnectionFoundation(cf -> cf.setClassLoaderProvider(ClassLoaderProvider.New(
                Thread.currentThread().getContextClassLoader())));

        // Specialised Handlers
        //storageFoundation.onConnectionFoundation(BinaryHandlersJDK17::registerJDK17TypeHandlers);

        return storageFoundation.createEmbeddedStorageManager().start();
    }
}
