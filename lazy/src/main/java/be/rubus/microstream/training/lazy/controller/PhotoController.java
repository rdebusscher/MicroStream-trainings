package be.rubus.microstream.training.lazy.controller;

import be.rubus.microstream.training.lazy.database.DB;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/photos")
@Singleton
public class PhotoController {

    @GET
    public Response getAllPhotos() {
        return Response.ok(DB.getRoot().getPhotos()).build();
    }
}
