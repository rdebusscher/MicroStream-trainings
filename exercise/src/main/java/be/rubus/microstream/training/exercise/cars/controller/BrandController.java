package be.rubus.microstream.training.exercise.cars.controller;

import be.rubus.microstream.training.exercise.cars.database.DB;
import be.rubus.microstream.training.exercise.cars.model.Brand;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/brand")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrandController {

    @GET
    public List<Brand> allBrands() {
        return DB.getRoot().getBrands();
    }

    @POST
    public Response addBrand(Brand dto) {
        Optional<Brand> existingBrand = DB.getRoot().getBrands().stream()
                .filter(b -> b.getName().equalsIgnoreCase(dto.getName()))
                .findAny();
        if (existingBrand.isPresent()) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        DB.getRoot().getBrands().add(dto);
        DB.getInstance().store(DB.getRoot().getBrands());
        return Response.ok(dto).build();

    }

    @PUT
    @Path("/{name}")
    public Response updateBrand(@PathParam("name") String brandName, Brand dto) {

        Optional<Brand> existingBrand = DB.getRoot().getBrands().stream()
                .filter(b -> b.getName().equalsIgnoreCase(brandName))
                .findAny();
        if (existingBrand.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Brand brand = existingBrand.get();
        brand.setName(dto.getName());
        DB.getInstance().store(brand);

        return Response.ok(existingBrand).build();

    }
}
