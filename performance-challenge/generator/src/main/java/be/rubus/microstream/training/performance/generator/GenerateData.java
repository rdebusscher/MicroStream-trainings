package be.rubus.microstream.training.performance.generator;

import be.rubus.microstream.training.performance.generator.config.DataSize;
import be.rubus.microstream.training.performance.generator.data.DataMetrics;
import be.rubus.microstream.training.performance.generator.data.RandomDataGenerator;
import be.rubus.microstream.training.performance.microstream.StorageManagerFactory;
import be.rubus.microstream.training.performance.microstream.database.Data;
import be.rubus.microstream.training.performance.utils.ChannelUtil;
import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenerateData {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(GenerateData.class);

        Data root = new Data();

        try (StorageManager storageManager =
                     StorageManagerFactory.create("bookstore", ChannelUtil.channelCount(), root)) {

            DataMetrics metrics = new RandomDataGenerator(
                    root.books(),
                    root.shops(),
                    root.customers(),
                    root.purchases(),
                    DataSize.MEDIUM,  // Change if you need a larger set
                    storageManager)
                    .generate();

            storageManager.setRoot(root);
            storageManager.storeRoot();

            logger.info("Random data generated: " + metrics.toString());
        }

    }
}
