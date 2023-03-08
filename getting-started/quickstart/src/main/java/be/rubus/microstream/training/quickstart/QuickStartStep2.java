package be.rubus.microstream.training.quickstart;

import be.rubus.microstream.training.quickstart.model.DataRoot;
import be.rubus.microstream.training.quickstart.model.Person;
import one.microstream.storage.types.StorageManager;

import java.util.List;
import java.util.stream.Collectors;

public class QuickStartStep2 {

    public static void main(String[] args) {
        DataRoot root = new DataRoot();
        try (StorageManager storageManager = Config.createStorageManager(root)) {
            List<Person> people = root.getPersons().stream()
                    .filter(p -> p.getName().startsWith("Jane"))
                    .collect(Collectors.toList());

            System.out.println(people.size());
            System.out.println(people.get(0));
        }
    }
}
