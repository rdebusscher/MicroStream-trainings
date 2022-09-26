package be.rubus.microstream.training.exercise.cars.controller;

import be.rubus.microstream.training.exercise.cars.database.DB;
import be.rubus.microstream.training.exercise.cars.database.Ids;
import be.rubus.microstream.training.exercise.cars.model.Brand;
import be.rubus.microstream.training.exercise.cars.model.Car;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/car")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarController {

    @GET
    public List<Car> allCars() {
        return DB.getRoot().getCars();
    }

    @GET
    @Path("/byModel/{name}")
    public List<Car> byModel(@PathParam("name") String name) {
        return DB.getRoot().getCars().stream()
                .filter(c -> c.getModel().startsWith(name))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/byBrand/{name}")
    public List<Car> byBrand(@PathParam("name") String name) {
        return DB.getRoot().getCars().stream()
                .filter(c -> c.getBrand().getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Car byId(@PathParam("id") Long id) {
        return DB.getRoot().getCars().stream()
                .filter(c -> c.getId().equals(id))
                .findAny().orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @POST
    public Response addCar(Car dto) {
        if (dto.getBrand() == null || dto.getBrand().getName() == null) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Brand missing").build();
        }
        // Does brand exist ?
        Optional<Brand> dbBrand = DB.getRoot().getBrands().stream()
                .filter(b -> b.getName().equalsIgnoreCase(dto.getBrand().getName()))
                .findAny();
        if (dbBrand.isEmpty()) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Brand not found").build();
        }
        Optional<Car> dbCar = DB.getRoot().getCars().stream()
                .filter(c -> c.getBrand().equals(dbBrand.get()))
                .filter(c -> c.getModel().equalsIgnoreCase(dto.getModel()))
                .findAny();
        if (dbCar.isPresent()) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Car already created").build();
        }

        Car entity = createEntity(dto, dbBrand.get());
        DB.getRoot().getCars().add(entity);
        DB.getInstance().store(DB.getRoot().getCars());
        return Response.ok(entity).build();
    }

    private Car createEntity(Car car, Brand brand) {
        Car result = new Car(Ids.nextCarId());
        result.setBrand(brand);
        result.setModel(car.getModel());
        result.setPrice(car.getPrice());
        return result;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        List<Car> cars = DB.getRoot().getCars();
        Optional<Car> dbCar = cars.stream()
                .filter(c -> c.getId().equals(id))
                .findAny();
        if (dbCar.isPresent()) {
            cars.remove(dbCar.get());
            DB.getInstance().store(cars);
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
