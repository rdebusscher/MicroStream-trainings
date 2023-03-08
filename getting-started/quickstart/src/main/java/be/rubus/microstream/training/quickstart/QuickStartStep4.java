package be.rubus.microstream.training.quickstart;

import be.rubus.microstream.training.quickstart.model.DataRoot;
import be.rubus.microstream.training.quickstart.model.Person;
import one.microstream.storage.types.StorageManager;

import java.util.List;

public class QuickStartStep4 {

    public static void main(String[] args) {
        DataRoot root = new DataRoot();
        try (StorageManager storageManager = Config.createStorageManager(root)) {

            List<Person> persons = root.getPersons();
            persons.remove(persons.size() - 1);  // remove last

            storageManager.store(persons);

        }
    }
}
