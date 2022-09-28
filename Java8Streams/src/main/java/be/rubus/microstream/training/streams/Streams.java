package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Streams {

    public static void main(String[] args) {
        List<Book> books = DataSet.getBooks();

        //createIntStream();
        //createStreamOnArray();
        //consumeStream(books);
        //createArrayCollector(books);
        //createListCollector(books);
        //createSetCollector(books);
        //System.out.println(createStringCollector());
        //System.out.println(findAny(books));
        //System.out.println(findFirst(books));
        System.out.println(booksPriceSum(books));
        //System.out.println(booksGroupPriceSum(books));
    }

    private static void createIntStream() {
        // of individual element
        // Of a range


    }


    private static void createStreamOnArray() {
        Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        // Convert Array to Stream

    }


    private static void consumeStream(List<Book> books) {

    }


    private static Book[] createArrayCollector(List<Book> books) {
        return null;
    }


    private static List<Book> createListCollector(List<Book> books) {
        return null;
    }


    private static Set<Book> createSetCollector(List<Book> books) {
        return null;
    }


    private static String createStringCollector() {
        List<String> petList = Arrays.asList("Cat", "Dog", "Mouse", "Bird");

        return null;
    }


    private static Book findAny(List<Book> books) {
        return null;
    }


    private static Book findFirst(List<Book> books) {
        return null;
    }


    private static BigDecimal booksPriceSum(List<Book> books) {

        return null;
    }


    private static Map<Author, Long> booksGroupAuthorCount(List<Book> books) {

        return null;
    }
}
