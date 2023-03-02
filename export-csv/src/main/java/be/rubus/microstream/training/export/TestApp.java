package be.rubus.microstream.training.export;

import be.rubus.microstream.training.export.model.InitData;
import be.rubus.microstream.training.export.model.Root;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.collections.types.XSequence;
import one.microstream.storage.types.StorageConnection;
import one.microstream.storage.types.StorageEntityTypeExportFileProvider;
import one.microstream.storage.types.StorageEntityTypeExportStatistics;
import one.microstream.storage.types.StorageManager;
import one.microstream.util.cql.CQL;

import java.nio.file.Path;
import java.nio.file.Paths;

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
