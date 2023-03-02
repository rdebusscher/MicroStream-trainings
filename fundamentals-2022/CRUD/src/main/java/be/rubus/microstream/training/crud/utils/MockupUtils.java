package be.rubus.microstream.training.crud.utils;

import be.rubus.microstream.training.crud.domain.Author;
import be.rubus.microstream.training.crud.domain.Book;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MockupUtils {
    public static List<Book> loadMockupData() {
        List<Book> books;
        List<Author> authors;

        try {
            Jsonb jsonb = JsonbBuilder.create();

            String authorsJSON = readContent("/mockup/Authors.json");
            authors = jsonb.fromJson(authorsJSON, new ArrayList<Author>() {
            }.getClass().getGenericSuperclass());

            String booksJSON = readContent("/mockup/Books.json");
            books = jsonb.fromJson(booksJSON, new ArrayList<Book>() {
            }.getClass().getGenericSuperclass());

            books.forEach(book ->
                    book.setAuthor(authors.stream().filter(a -> a.getId().equals(book.getAuthorId())).findAny().get()));

            jsonb.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return books;
    }

    private static String readContent(String resourceLocation) throws IOException {
        URL url = MockupUtils.class.getResource(resourceLocation);
        if (url == null) {
            throw new FileNotFoundException(resourceLocation);
        }
        Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
        String result = scanner.next();
        scanner.close();
        return result;
    }

}
