package be.rubus.microstream.training.quickstart.lazy;

import be.rubus.microstream.training.quickstart.lazy.model.DataRoot;
import one.microstream.storage.types.StorageManager;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

public class NonLazyTest {

    public static void main(String[] args) {
        DataRoot root = new DataRoot();
        long startManager = System.currentTimeMillis();
        try (StorageManager storageManager = Config.createStorageManager(root, "non-lazy")) {
            long endManager = System.currentTimeMillis();

            if (root.getValues().isEmpty()) {
                storeTestData(storageManager, root.getValues());
                return;
            }

            long startCalculation = System.currentTimeMillis();
            OptionalDouble average = root.getValues().stream()
                    .mapToDouble(d -> d)
                    .average();
            long endCalculation = System.currentTimeMillis();

            System.out.printf("Start StorageManager time %s ms%n", (endManager - startManager));
            System.out.printf("Calculation time %s ms%n", (endCalculation - startCalculation));
            System.out.printf("Average %s%n", average.getAsDouble());
        }
    }

    private static void storeTestData(StorageManager storageManager, List<Double> values) {
        Random random = new Random();
        for (int i = 0; i < 1_000_000; i++) {
            values.add(random.nextDouble());
        }
        storageManager.store(values);
    }
}
