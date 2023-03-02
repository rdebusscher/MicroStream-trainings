package be.rubus.microstream.training.export;

import be.rubus.microstream.training.export.model.Root;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.afs.types.AReadableFile;
import one.microstream.collections.types.XSequence;
import one.microstream.storage.types.*;
import one.microstream.util.cql.CQL;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateCSV {

    public static void main(String[] args) {

        Root root = new Root();
        try (StorageManager storageManager = StorageManagerFactory.createStorageManager(root)) {
            // StorageManager required for the Type Dictionary.

            String binaryTarget = "export-bin";
            XSequence<Path> files = createBinaryExport(storageManager, binaryTarget);

            NioFileSystem fileSystem = NioFileSystem.New();

            String csvTarget = "csv";
            StorageDataConverterTypeBinaryToCsv converter = getCSVConverter(storageManager, fileSystem, csvTarget);

            for (Path file : files) {
                convertToCSV(fileSystem, converter, binaryTarget + "/" + file.getFileName());
            }

        }
    }

    private static StorageDataConverterTypeBinaryToCsv getCSVConverter(StorageManager storageManager, NioFileSystem fileSystem, String csvDirectory) {
        StorageDataConverterTypeBinaryToCsv converter =
                new StorageDataConverterTypeBinaryToCsv.UTF8(
                        StorageDataConverterCsvConfiguration.defaultConfiguration(),
                        new StorageEntityTypeConversionFileProvider.Default(
                                fileSystem.ensureDirectoryPath(csvDirectory),
                                "csv"
                        ),
                        storageManager.typeDictionary(),
                        null, // no type name mapping
                        4096, // read buffer size
                        4096  // write buffer size
                );
        return converter;
    }

    private static void convertToCSV(NioFileSystem fileSystem, StorageDataConverterTypeBinaryToCsv converter, String binaryFile) {
        AReadableFile dataFile = fileSystem.ensureFilePath(binaryFile).useReading();
        try {
            converter.convertDataFile(dataFile);
        } finally {
            dataFile.close();
        }
    }

    private static XSequence<Path> createBinaryExport(StorageManager storageManager, String binaryTarget) {
        NioFileSystem fileSystem = NioFileSystem.New();

        String fileSuffix = "bin";
        StorageConnection connection = storageManager.createConnection();
        StorageEntityTypeExportStatistics exportResult = connection.exportTypes(
                new StorageEntityTypeExportFileProvider.Default(
                        fileSystem.ensureDirectoryPath(binaryTarget),
                        fileSuffix
                ),
                typeHandler -> true // export all, customize if necessary
        );
        return CQL
                .from(exportResult.typeStatistics().values())
                .project(s -> Paths.get(s.file().identifier()))
                .execute();

    }
}
