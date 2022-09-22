package be.rubus.microstream.training.performance;

import java.util.StringJoiner;

public class Range<T> {

    private final T lowerBound;
    private final T upperbound;

    public Range(T lowerBound, T upperbound) {
        this.lowerBound = lowerBound;
        this.upperbound = upperbound;
    }

    public T getLowerBound() {
        return lowerBound;
    }

    public T getUpperbound() {
        return upperbound;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Range.class.getSimpleName() + "[", "]")
                .add("lowerBound=" + lowerBound)
                .add("upperbound=" + upperbound)
                .toString();
    }
}
