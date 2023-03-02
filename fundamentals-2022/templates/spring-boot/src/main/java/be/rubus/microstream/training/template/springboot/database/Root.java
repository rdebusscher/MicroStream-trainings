package be.rubus.microstream.training.template.springboot.database;


import be.rubus.microstream.training.template.springboot.model.Product;

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
