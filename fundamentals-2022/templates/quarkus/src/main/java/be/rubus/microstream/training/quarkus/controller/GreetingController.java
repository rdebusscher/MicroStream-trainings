package be.rubus.microstream.training.quarkus.controller;

import be.rubus.microstream.training.quarkus.database.DB;
import be.rubus.microstream.training.quarkus.model.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello")
public class GreetingController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        return DB.getRoot().getProducts();
    }

}