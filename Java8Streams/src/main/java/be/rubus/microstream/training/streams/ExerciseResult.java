package be.rubus.microstream.training.streams;

import be.rubus.microstream.training.streams.model.Address;
import be.rubus.microstream.training.streams.model.Author;
import be.rubus.microstream.training.streams.model.Book;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ExerciseResult {

    public static void main(String[] args) {
        List<Book> books = DataSet.getBooks();
        //System.out.println(getBooksFromFemaleAuthor(books));
        //System.out.println(getAuthorsFromPoland(books));
        //System.out.println(getBooksBefore2012(books));
    }

    private static List<Book> getBooksFromFemaleAuthor(List<Book> books) {

        return books.stream()
                .filter(b -> b.getAuthor().getGender().equals("Female")).collect(
                        Collectors.toList());
    }


    private static List<Author> getAuthorsFromPoland(List<Book> books) {
        Predicate<Address> polandPredicate = a -> a.getCountry().equalsIgnoreCase("Poland");

        return books.stream().map(b -> b.getAuthor())
                .filter(au -> au.getAddresses().stream()
                        .anyMatch(polandPredicate))
                .collect(Collectors.toList());
    }


    private static List<Book> getBooksBefore2012(List<Book> books) {
        LocalDate startDate = LocalDate.of(2012, Month.JANUARY, 1);

        return books.stream()
                .filter(b -> b.getReleaseDate().isBefore(startDate)).collect(Collectors.toList());
    }
}
