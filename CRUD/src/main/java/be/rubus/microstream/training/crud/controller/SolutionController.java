package be.rubus.microstream.training.crud.controller;


import be.rubus.microstream.training.crud.domain.Author;
import be.rubus.microstream.training.crud.domain.Book;
import be.rubus.microstream.training.crud.storage.DB;
import be.rubus.microstream.training.crud.utils.MockupUtils;
import one.microstream.persistence.types.Storer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("/solution/books")
@Produces(MediaType.APPLICATION_JSON)
public class SolutionController {

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
        List<Book> books = DB.getRoot().getBooks();
        books.clear();
        books.addAll(mockupData);
        DB.getInstance().store(books);

        return Response.ok("Books successfully created!").build();
    }

    @GET
    @Path("/startsWith_A")
    public List<Book> getBooksStartingWithA() {
        // Enter your code here
        List<Book> result = DB.getRoot().getBooks().stream()
                .filter(b -> b.getName().startsWith("A"))
                .collect(Collectors.toList());

        return result;
    }

    @GET
    @Path("/createSingle")
    public Response createSingleBook() {
        Author author = new Author(100L, "John", "Doe", "j.doe@example.com", "Male");
        Book book = new Book("123456789", "Single Book", LocalDate.now(),  BigDecimal.valueOf(13.32), author);

        // Enter your code here
        List<Book> books = DB.getRoot().getBooks();
        books.add(book);
        DB.getInstance().store(books);

        return Response.ok("Book successfully created!").build();
    }

    @GET
    @Path("/isbn")
    public Book Isbn(@QueryParam("value") String isbn) {

        // Enter your code here
        List<Book> result = DB.getRoot().getBooks().stream()
                .filter(b -> isbn.equals(b.getIsbn()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }


    @GET
    @Path("/clear")
    public Response clearBooks() {
        // Enter your code here
        List<Book> books = DB.getRoot().getBooks();
        books.clear();
        DB.getInstance().store(books);

        return Response.ok("Books successfully cleared!").build();
    }

    @GET
    @Path("/updateSingle")
    public Response updateSingleBook() {
        // Enter your code here
        Optional<Book> queryResult = DB.getRoot().getBooks().stream()
                .filter(b -> "123456789".equals(b.getIsbn()))
                .findAny();
        Book book = queryResult.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));

        book.setReleaseDate(LocalDate.now());
        DB.getInstance().store(book);

        return Response.ok("Book successfully updated!").build();
    }

    @GET
    @Path("/updateMulti")
    public Response updateMultiBooks() {
        // Enter your code here
        Storer storer = DB.getInstance().createLazyStorer();

        DB.getRoot().getBooks().stream().filter(b -> b.getName().startsWith("A")).forEach(b ->
        {
            BigDecimal value = b.getPrice().multiply(BigDecimal.valueOf(0.9));
            b.setPrice(value);

            storer.store(b);
        });

        storer.commit();

        return Response.ok("Books successfully updated!").build();
    }


}
