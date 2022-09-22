package be.rubus.microstream.training.performance;

import java.util.List;

public class QueryInformation<T> {

    private final List<T> results;
    private final long elapsedNanoSeconds;

    private final long mappingNanoSeconds;

    public QueryInformation(List<T> results, long elapsedNanoSeconds) {
        this(results, elapsedNanoSeconds, 0);
    }
    public QueryInformation(List<T> results, long elapsedNanoSeconds, long mappingNanoSeconds) {
        this.results = results;
        this.elapsedNanoSeconds = elapsedNanoSeconds;
        this.mappingNanoSeconds = mappingNanoSeconds;
    }

    public List<T> getResults() {
        return results;
    }

    public long getElapsedNanoSeconds() {
        return elapsedNanoSeconds;
    }

    public long getMappingNanoSeconds() {
        return mappingNanoSeconds;
    }
}
