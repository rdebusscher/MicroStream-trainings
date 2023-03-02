package be.rubus.microstream.training.quarkus.database;


import be.rubus.microstream.training.quarkus.model.Product;

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
