package be.rubus.microstream.training.template.springboot.controller;

import be.rubus.microstream.training.template.springboot.database.DB;
import be.rubus.microstream.training.template.springboot.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return DB.getRoot().getProducts();
    }

}
