package be.rubus.microstream.training.exercise.cars.controller;

import be.rubus.microstream.training.exercise.cars.database.DB;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
@Singleton
public class HelloController {

    @GET
    public String helloWorld() {
        return DB.getRoot().getName();
    }


}
