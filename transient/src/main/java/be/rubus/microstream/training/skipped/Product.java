package be.rubus.microstream.training.skipped;

import java.util.StringJoiner;

public class Product {

    private final String code;
    private final String name;
    private final transient String ignored;
    private final String _internalValue;

    public Product(String code, String name, String ignored, String internalValue) {
        this.code = code;
        this.name = name;
        this.ignored = ignored;
        _internalValue = internalValue;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIgnored() {
        return ignored;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .add("name='" + name + "'")
                .add("ignored='" + ignored + "'")
                .add("_internal='" + _internalValue + "'")
                .toString();
    }
}
