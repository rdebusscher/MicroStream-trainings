package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;

import java.util.List;


public class Exercise {

    public static void main(String[] args) {
        List<Book> books = DataSet.getBooks();
        //System.out.println(getBooksFromFemaleAuthor(books));
        //System.out.println(getAuthorsFromPoland(books));
        //System.out.println(getBooksBefore2012(books));
    }

    private static List<Book> getBooksFromFemaleAuthor(List<Book> books) {

        return null;
    }


    private static List<Author> getAuthorsFromPoland(List<Book> books) {
        return null;
    }


    private static List<Book> getBooksBefore2012(List<Book> books) {
        return null;
    }
}
