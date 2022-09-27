package be.rubus.microstream.training.skipped;

import java.util.StringJoiner;

public class Product {

    private final String code;
    private final String name;
    private final transient String ignored;

    public Product(String code, String name, String ignored) {
        this.code = code;
        this.name = name;
        this.ignored = ignored;
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
                .toString();
    }
}
