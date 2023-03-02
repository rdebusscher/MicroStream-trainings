package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Address;
import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookStreamsResult {

    public static void main(String[] args) {
        List<Book> books = DataSet.getBooks();

        //System.out.println(getBooksSortedByName(books).subList(0,5));
        //System.out.println(getBooksSortedByPriceDescending(books).subList(0, 5));
        //System.out.println(getBooksByTitle(books));
        //System.out.println(getBooksWithA(books).subList(0,5));
        //System.out.println(getBooksByTitlePredicate(books));

        //Collection<Author> allAuthors = getAllAuthors(books);
        //System.out.println(allAuthors.size());
        //System.out.println(allAuthors);

        //Collection<Address> allAddresses = getAllAddresses(books);
        //System.out.println(allAddresses.size());
    }

    private static List<Book> getBooksSortedByName(List<Book> books) {
        return books.stream().sorted(Comparator.comparing(Book::getName)).collect(Collectors.toList());

    }

    private static List<Book> getBooksSortedByPriceDescending(List<Book> books) {
        return books.stream().sorted(Comparator.comparing(Book::getPrice).reversed()).collect(Collectors.toList());

    }


    private static List<Book> getBooksByTitle(List<Book> books) {
        return books.stream().filter(b -> b.getName().equals("Bangkok Dangerous")).collect(
                Collectors.toList());
    }

    private static List<Book> getBooksWithA(List<Book> books) {
        return books.stream().filter(b -> b.getName().startsWith("A"))
                .collect(Collectors.toList());
    }

    private static List<Book> getBooksByTitlePredicate(List<Book> books) {
        Predicate<Book> namePredicate = b -> b.getName().equals("Bangkok Dangerous");
        Predicate<Book> isbnPredicate = b -> b.getIsbn().equals("12345");
        Predicate<Book> nameAndISBNPredicate = namePredicate.and(isbnPredicate);

        return books.stream().filter(nameAndISBNPredicate).collect(
                Collectors.toList());
    }


    private static Collection<Author> getAllAuthors(List<Book> books) {
        return books.stream().map(c -> c.getAuthor()).collect(Collectors.toList());

    }

    private static Collection<Address> getAllAddresses(List<Book> books) {
        return
                getAllAuthors(books).stream().flatMap(a -> a.getAddresses().stream()).collect(Collectors.toSet());
    }


}
