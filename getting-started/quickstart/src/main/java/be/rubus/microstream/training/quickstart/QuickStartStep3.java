package be.rubus.microstream.training.quickstart;

import be.rubus.microstream.training.quickstart.model.DataRoot;
import be.rubus.microstream.training.quickstart.model.Person;
import one.microstream.storage.types.StorageManager;

import java.util.Date;

public class QuickStartStep3 {

    public static void main(String[] args) {
        DataRoot root = new DataRoot();
        try (StorageManager storageManager = Config.createStorageManager(root)) {
            Person jane = root.getPersons().stream()
                    .filter(p -> p.getName().startsWith("Jane"))
                    .findAny().orElseThrow();

            jane.setAge(34);

            root.setTimeStamp(new Date());

            storageManager.store(jane);
            // When we don't store root, timestamp is not 'saved'

        }
    }
}
