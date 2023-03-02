package be.rubus.microstream.training.export;

import be.rubus.microstream.training.export.model.InitData;
import be.rubus.microstream.training.export.model.Root;
import one.microstream.storage.types.StorageManager;

public class TestApp {

    public static void main(String[] args) {
        Root root = new Root();
        try (StorageManager storageManager = StorageManagerFactory.createStorageManager(root)) {
            InitData.ensureDefaultData(storageManager);

            System.out.println(root.getBooks());
            System.out.println("Number of books " + root.getBooks().size());
        }
    }

}
