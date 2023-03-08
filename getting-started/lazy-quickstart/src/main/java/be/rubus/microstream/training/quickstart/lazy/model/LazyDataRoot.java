package be.rubus.microstream.training.quickstart.lazy.model;

import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;

public class LazyDataRoot {

    private final Lazy<List<Double>> values = Lazy.Reference(new ArrayList<>());

    public Lazy<List<Double>> getValues() {
        return values;
    }
}
