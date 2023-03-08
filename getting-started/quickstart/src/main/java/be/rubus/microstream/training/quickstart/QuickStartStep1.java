package be.rubus.microstream.training.quickstart;

import be.rubus.microstream.training.quickstart.model.DataRoot;
import be.rubus.microstream.training.quickstart.model.PersonBuilder;
import one.microstream.storage.types.StorageManager;

import java.util.Date;

public class QuickStartStep1 {

    public static void main(String[] args) {
        DataRoot root = new DataRoot();
        try (StorageManager storageManager = Config.createStorageManager(root)) {
            root.getPersons().add(
                    new PersonBuilder()
                            .withId(1L)
                            .withName("John Doe")
                            .withAge(42)
                            .build());

            root.getPersons().add(
                    new PersonBuilder()
                            .withId(2L)
                            .withName("Jane Doe")
                            .withAge(24)
                            .build());

            root.getPersons().add(
                    new PersonBuilder()
                            .withId(3L)
                            .withName("Me")
                            .withAge(99)
                            .build());

            root.setTimeStamp(new Date());

            storageManager.store(root);
            storageManager.store(root.getPersons());
        }
    }
}
