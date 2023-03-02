package be.rubus.microstream.training.performance.generator.insert;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

public final class StreamUtil {

    private StreamUtil() {
    }

    public static <T> Stream<T> concat(Stream<T>... streams) {
        return Stream.of(streams)
                .reduce(Stream::concat)
                .orElseGet(Stream::empty);
    }

    public static <T> Collector<T, ?, EntityIdMap<T>> toEntityIdMap() {
        return toEntityIdMap(new AutoIncrement());
    }

    public static <T> Collector<T, ?, EntityIdMap<T>> toEntityIdMap(AutoIncrement id) {
        return Collectors.toMap(identity(), e -> id.next(), (u, v) -> u, EntityIdMap::new);
    }


}
