package be.rubus.microstream.training.payara.controller;

import be.rubus.microstream.training.payara.database.DB;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/hello")
@Singleton
public class HelloController {

    @GET
    @Path("/hello")
    public String helloWorld() {


        // Enter your code here

        return "Hello World";
    }

    @GET
    @Path("/products")
    public Response getAllProducts() {
        return Response.ok(DB.getRoot().getProducts()).build();
    }
}
