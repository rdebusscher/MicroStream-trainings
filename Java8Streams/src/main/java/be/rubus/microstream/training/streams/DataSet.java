package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Address;
import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DataSet {

    private DataSet() {
    }

    public static List<Book> getBooks() {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            List<Address> addresses = readJSON(mapper, "/mockup/address.json", Address.class);
            List<Author> authors = readJSON(mapper, "/mockup/Authors.json", Author.class);
            List<Book> books = readJSON(mapper, "/mockup/Books.json", Book.class);

            books.forEach(book ->
                    book.setAuthor(authors.stream().filter(a -> a.getId().equals(book.getAuthorId())).findAny().get()));

            assignRandomAddress(authors, addresses);

            return books;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void assignRandomAddress(List<Author> authors, List<Address> addresses) {
        authors.forEach(author ->
        {

            Collections.shuffle(addresses);
            int randomSeriesLength = 3;
            List<Address> subList = addresses.subList(0, randomSeriesLength);

            author.getAddresses().addAll(subList);

        });

    }

    private static <T> List<T> readJSON(ObjectMapper mapper, String resourceLocation, Class<T> clazz) throws IOException {
        InputStream stream = DataSet.class.getResourceAsStream(resourceLocation);
        if (stream == null) {
            throw new FileNotFoundException(resourceLocation);
        }

        Class<?> arrayClazz = Array.newInstance(clazz, 0).getClass();

        Object[] o = (Object[]) mapper.readValue(stream, arrayClazz);
        List<T> result = (List<T>) Arrays.asList(o);

        stream.close();

        return result;
    }


}
