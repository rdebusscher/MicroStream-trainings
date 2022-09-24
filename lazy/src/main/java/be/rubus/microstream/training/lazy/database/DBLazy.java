package be.rubus.microstream.training.lazy.database;

import be.rubus.microstream.training.lazy.model.LazyPhoto;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class DBLazy {

    private static StorageManager INSTANCE = null;

    private DBLazy() {

    }

    private static StorageManager createStorageManager(LazyRoot root) {
        StorageManager result = EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-lazy-data")
                .setChannelCount(1)

                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();
        initData(result, root);
        return result;
    }

    private static void initData(StorageManager storageManager, LazyRoot root) {
        if (root.getPhotos().isEmpty()) {
            try {
                root.addPhoto(new LazyPhoto(48099, "House", getImage("/48099.jpeg") ));
                root.addPhoto(new LazyPhoto(87540, "Clouds", getImage("/87540.jpg") ));
                root.addPhoto(new LazyPhoto(297894, "Sea", getImage("/297894.jpeg") ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            storageManager.store(root.getPhotos());
        }
    }

    private static byte[] getImage(String imageName) throws IOException {
        InputStream stream = DBLazy.class.getResourceAsStream(imageName);
        if (stream == null) {
            throw new FileNotFoundException(imageName);
        }
        return stream.readAllBytes();
    }

    public static StorageManager getInstance() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            LazyRoot root = new LazyRoot();
            INSTANCE = createStorageManager(root);
        }
        return INSTANCE;
    }

    public static LazyRoot getRoot() {
        // This is not thread safe but for training, we assume only 1 user, the developer and thus no concurrency.
        if (INSTANCE == null) {
            LazyRoot root = new LazyRoot();
            INSTANCE = createStorageManager(root);
        }
        return (LazyRoot) INSTANCE.root();
    }
}
