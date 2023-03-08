package be.rubus.microstream.training.quickstart.lazy;

import be.rubus.microstream.training.quickstart.lazy.model.LazyDataRoot;
import one.microstream.reference.Lazy;
import one.microstream.storage.types.StorageManager;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

public class LazyTest {

    public static void main(String[] args) {
        LazyDataRoot root = new LazyDataRoot();
        long startManager = System.currentTimeMillis();
        try (StorageManager storageManager = Config.createStorageManager(root, "lazy")) {
            long endManager = System.currentTimeMillis();

            long startCalculation = System.currentTimeMillis();

            Lazy<List<Double>> lazyList = root.getValues();
            if (lazyList.get().isEmpty()) {  // This loads the data!!
                storeTestData(storageManager, lazyList.get());
                return;
            }

            OptionalDouble average = lazyList.get().stream()
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
