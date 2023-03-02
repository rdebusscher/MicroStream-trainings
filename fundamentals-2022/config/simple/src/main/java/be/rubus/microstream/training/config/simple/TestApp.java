package be.rubus.microstream.training.config.simple;

import be.rubus.microstream.training.config.model.InitData;
import be.rubus.microstream.training.config.model.Root;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.types.StorageManager;

import java.nio.file.Paths;

public class TestApp {

    public static void main(String[] args) {
        Root root = new Root();
        try (StorageManager storageManager = EmbeddedStorage.start(root, Paths.get("simpleConfig"))) {
            InitData.ensureDefaultData(storageManager);

            System.out.println(root.getBooks());
            System.out.println("Number of books " + root.getBooks().size());
        }
    }
}
