package be.rubus.microstream.training.performance.microstream.tests;

import be.rubus.microstream.training.performance.QueryInformation;
import be.rubus.microstream.training.performance.StopWatch;
import be.rubus.microstream.training.performance.microstream.Service;
import be.rubus.microstream.training.performance.microstream.StorageManagerFactory;
import be.rubus.microstream.training.performance.microstream.database.Data;
import be.rubus.microstream.training.performance.microstream.database.model.Purchase;
import be.rubus.microstream.training.performance.utils.ChannelUtil;
import be.rubus.microstream.training.performance.utils.DurationUtil;
import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class PurchaseOfForeigners {

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("one.microstream").setLevel(Level.OFF);

        Logger logger = LoggerFactory.getLogger(PurchaseOfForeigners.class);
        logger.info("Performance run MicroStream");

        Data root = new Data();

        StopWatch stopWatch = StopWatch.start();

        try (StorageManager storageManager =
                     StorageManagerFactory.create("bookstore", ChannelUtil.channelCount(), root)) {

            DurationUtil.printDuration(logger, "Database loading", stopWatch.stop());

            Service service = new Service(root);

            logger.info("Execute Query - purchases of Foreigners");
            QueryInformation<Purchase> listQueryInformation = service.purchasesOfForeigners("DE", 2021);

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        }
    }
}
