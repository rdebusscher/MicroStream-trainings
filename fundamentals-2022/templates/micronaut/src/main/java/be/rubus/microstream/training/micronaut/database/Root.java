package be.rubus.microstream.training.micronaut.database;

import be.rubus.microstream.training.micronaut.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Root {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
