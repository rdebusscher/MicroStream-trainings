package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Address;
import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;

import java.util.Collection;
import java.util.List;

public class BookStreams {

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
        return null;

    }

    private static List<Book> getBooksSortedByPriceDescending(List<Book> books) {
        return null;

    }


    private static List<Book> getBooksByTitle(List<Book> books) {
        return null;
    }

    private static List<Book> getBooksWithA(List<Book> books) {
        return null;
    }

    private static List<Book> getBooksByTitlePredicate(List<Book> books) {

        return null;
    }


    private static Collection<Author> getAllAuthors(List<Book> books) {
        return null;

    }

    private static Collection<Address> getAllAddresses(List<Book> books) {
        return null;
    }


}
