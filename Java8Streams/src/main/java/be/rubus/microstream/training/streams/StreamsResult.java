package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamsResult {

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
        //System.out.println(booksPriceSum(books));
        //System.out.println(booksGroupAuthorCount(books));
    }

    private static void createIntStream() {
        IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).forEach(i -> System.out.println(i));
        System.out.println("---");
        IntStream.range(5, 10).forEach(i -> System.out.println(i));

    }


    private static void createStreamOnArray() {
        Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Stream.of(nums).forEach(i -> System.out.println(i));
        System.out.println("---");
        Arrays.stream(nums).forEach(System.out::println);

    }


    private static void consumeStream(List<Book> books) {
        books.stream().forEach(b -> System.out.println(b.getName()));
    }


    private static Book[] createArrayCollector(List<Book> books) {
        return books.stream().toArray(Book[]::new);
    }


    private static List<Book> createListCollector(List<Book> books) {
        return books.stream().collect(Collectors.toList());
    }


    private static Set<Book> createSetCollector(List<Book> books) {
        return books.stream().collect(Collectors.toSet());
    }


    private static String createStringCollector() {
        List<String> petList = Arrays.asList("Cat", "Dog", "Mouse", "Bird");
        return petList.stream().collect(Collectors.joining(","));
    }


    private static Book findAny(List<Book> books) {
        return books.stream().findAny().get();
    }


    private static Book findFirst(List<Book> books) {
        return books.stream().findFirst().get();
    }


    private static BigDecimal booksPriceSum(List<Book> books) {

        return books.stream().collect(Collectors.reducing(BigDecimal.ZERO, Book::getPrice, BigDecimal::add));
    }


    private static Map<Author, Long> booksGroupAuthorCount(List<Book> books) {
        // Count the number of books for each author
        return books.stream().collect(Collectors.groupingBy(
                Book::getAuthor,
                Collectors.reducing(0L, b -> 1L, Long::sum)));
    }
}
