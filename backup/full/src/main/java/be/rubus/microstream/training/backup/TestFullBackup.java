package be.rubus.microstream.training.backup;

import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.types.StorageManager;

import java.util.List;

public class TestFullBackup {

    public static void main(String[] args) {
        Root root = new Root();
        try (StorageManager storageManager = createStorageManager(root)) {

            List<Long> numberList = root.getData();
            long cnt = numberList.size();
            System.out.printf("Storage contains %s items%n", cnt);

            for (int i = 0; i < 1000; i++) {
                numberList.add(cnt + i);
                storageManager.store(numberList);
            }

            long start = System.currentTimeMillis();
            storageManager.issueFullBackup(NioFileSystem.New().ensureDirectoryPath( "backup", "full"));
            long end = System.currentTimeMillis();

            System.out.printf("Full backup performed in %s ms%n", end-start);

            for (int i = 0; i < 10; i++) {
                numberList.add(cnt + i);
                storageManager.store(numberList);
            }

            System.exit(-1); // Simulate crash, remove 'microstream-data' directory
        }

    }

    private static StorageManager createStorageManager(Root root) {
        // requires  microstream-storage-embedded-configuration dependency
        return EmbeddedStorageConfiguration.Builder()
                .setStorageDirectory("microstream-data")

                .createEmbeddedStorageFoundation()
                .setRoot(root)

                .createEmbeddedStorageManager()
                .start();
    }
}
