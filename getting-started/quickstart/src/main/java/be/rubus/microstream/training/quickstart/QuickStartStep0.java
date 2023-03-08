package be.rubus.microstream.training.quickstart;

import be.rubus.microstream.training.quickstart.model.DataRoot;
import one.microstream.storage.types.StorageManager;

public class QuickStartStep0 {

    public static void main(String[] args) {
        DataRoot root = new DataRoot();
        try (StorageManager storageManager = Config.createStorageManager(root)) {
            System.out.println(root);
        }
    }
}
