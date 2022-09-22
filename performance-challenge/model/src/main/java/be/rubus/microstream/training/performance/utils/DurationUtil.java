package be.rubus.microstream.training.performance.utils;

import org.slf4j.Logger;

import java.time.Duration;

public final class DurationUtil {

    private DurationUtil() {
    }

    public static void printDuration(String msg, long elapsed) {
        Duration queryTime = Duration.ofNanos(elapsed);
        System.out.printf(msg + "Query Execution took %s Second %s Millisecond %s Nanosecond %n",
                queryTime.toSecondsPart(),
                queryTime.toMillisPart(),
                queryTime.toNanosPart() % 1_000_000L);

    }

    public static void printDuration(Logger logger, String msg, long elapsed) {
        Duration queryTime = Duration.ofNanos(elapsed);
        logger.info( "{} took {} Second {} Millisecond {} Nanosecond", msg,
                queryTime.toSecondsPart(),
                queryTime.toMillisPart(),
                queryTime.toNanosPart() % 1_000_000L);

    }
}
