package be.rubus.microstream.training.lazy.controller;

import be.rubus.microstream.training.lazy.database.DBLazy;
import be.rubus.microstream.training.lazy.model.LazyPhoto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/lazy")
public class LazyPhotoController {

    @GET
    public Response getAllPhotos() {
        return Response.ok(DBLazy.getRoot().getPhotos()).build();
    }

    @GET
    @Path("/load")
    public Response loadImages() {
        DBLazy.getRoot().getPhotos().forEach(LazyPhoto::getImageData);
        return Response.ok("Lazy references loaded").build();
    }

    @GET
    @Path("/debug")
    public Response debug() {
        List<LazyPhoto> photos = DBLazy.getRoot().getPhotos();
        StringBuilder result = new StringBuilder();
        for (LazyPhoto photo : photos) {
            result.append(photo.getId()).append(" - ").append(photo.getImage().isLoaded()).append("\n");
        }
        return Response.ok(result.toString()).build();
    }

}
