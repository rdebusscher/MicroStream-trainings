package be.rubus.microstream.training.crud.controller;


import be.rubus.microstream.training.crud.domain.Author;
import be.rubus.microstream.training.crud.domain.Book;
import be.rubus.microstream.training.crud.storage.DB;
import be.rubus.microstream.training.crud.utils.MockupUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookController {

    @GET
    public List<Book> getBooks() {
        return DB.getRoot().getBooks();
    }

    @GET
    @Path("/create")
    public Response createBooks() {
        List<Book> mockupData = MockupUtils.loadMockupData();

        if (mockupData == null || mockupData.isEmpty()) {
            throw new WebApplicationException("Loading JSON data failed");
        }

        // Use mockupData in DataRoot and save data

        return Response.ok("Books successfully created!").build();
    }

    @GET
    @Path("/startsWith_A")
    public List<Book> getBooksStartingWithA() {
        // Enter your code here

        return null;
    }

    @GET
    @Path("/createSingle")
    public Response createSingleBook() {
        Author author = new Author(100L, "John", "Doe", "j.doe@example.com", "Male");
        Book book = new Book("123456789", "Single Book", LocalDate.now(), new BigDecimal("13.32"), author);

        // Enter your code here

        return Response.ok("Book successfully created!").build();
    }

    @GET
    @Path("/isbn")
    public Book Isbn(@QueryParam("value") String isbn) {

        // Enter your code here

        return null;
    }

    @GET
    @Path("/clear")
    public Response clearBooks() {
        // Enter your code here

        return Response.ok("Books successfully cleared!").build();
    }

    @GET
    @Path("/updateSingle")
    public Response updateSingleBook() {
        // Enter your code here

        return Response.ok("Book successfully updated!").build();
    }

    @GET
    @Path("/updateMulti")
    public Response updateMultiBooks() {
        // Enter your code here

        return Response.ok("Books successfully updated!").build();
    }


}
