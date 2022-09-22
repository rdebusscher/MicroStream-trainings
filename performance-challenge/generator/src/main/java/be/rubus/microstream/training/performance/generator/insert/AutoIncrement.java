package be.rubus.microstream.training.performance.generator.insert;

import java.util.concurrent.atomic.AtomicLong;

public class AutoIncrement {

    private final AtomicLong value = new AtomicLong();

    public long next() {
        return value.incrementAndGet();
    }


}
