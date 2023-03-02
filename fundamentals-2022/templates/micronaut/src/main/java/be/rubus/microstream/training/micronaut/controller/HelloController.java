package be.rubus.microstream.training.micronaut.controller;

import be.rubus.microstream.training.micronaut.database.DB;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HelloController {

    @Get("/hello")
    public HttpResponse<?> helloWorld() {


        // Enter your code here

        return HttpResponse.ok("Hello World");
    }

    @Get("/products")
    public HttpResponse<?> getAllProducts() {
        return HttpResponse.ok(DB.getRoot().getProducts());
    }
}
