package be.rubus.microstream.training.performance.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public final class DurationUtil {

    public static final Logger logger = LoggerFactory.getLogger(DurationUtil.class);

    private DurationUtil() {
    }

    public static void printDuration(String msg, long elapsed) {
        Duration queryTime = Duration.ofNanos(elapsed);
        logger.info(String.format(msg + "Query Execution took %s Second %s Millisecond %s Nanosecond",
                queryTime.toSecondsPart(),
                queryTime.toMillisPart(),
                queryTime.toNanosPart() % 1_000_000L));

    }

    public static void printDuration(Logger logger, String msg, long elapsed) {
        Duration queryTime = Duration.ofNanos(elapsed);
        logger.info( "{} took {} Second {} Millisecond {} Nanosecond", msg,
                queryTime.toSecondsPart(),
                queryTime.toMillisPart(),
                queryTime.toNanosPart() % 1_000_000L);

    }
}
