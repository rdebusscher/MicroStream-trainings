package be.rubus.microstream.training.performance.microstream.database;

import be.rubus.microstream.training.performance.concurrent.ReadWriteLocked;
import be.rubus.microstream.training.performance.model.*;
import one.microstream.persistence.types.Persister;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Range of all books sold by this company.
 * <p>
 * This type is used to read and write the {@link Book}s, their {@link Author}s, {@link Genre}s,
 * {@link Publisher}s and {@link Language}s.
 * <p>
 * All operations on this type are thread safe.
 *
 * @see Data#books()
 * @see ReadWriteLocked
 */
public class Books extends ReadWriteLocked {
    /*
     * Multiple maps holding references to the books, for a faster lookup.
     */
    private final Map<String, Book> isbn13ToBook = new HashMap<>();
    private final Map<Author, List<Book>> authorToBooks = new HashMap<>();
    private final Map<Genre, List<Book>> genreToBooks = new HashMap<>();
    private final Map<Publisher, List<Book>> publisherToBooks = new HashMap<>();
    private final Map<Language, List<Book>> languageToBooks = new HashMap<>();

    /**
     * Adds a range of new books and stores it with the given persister.
     *
     * @param books     the new books
     * @param persister the persister to store them with
     */
    public void addAll(Collection<? extends Book> books, Persister persister) {
        write(() -> {
            books.forEach(this::addToCollections);
            storeCollections(persister);
        });
    }

    /**
     * Stores all collections of this implementation with the given persister.
     *
     * @param persister the MicroStream persister used to store the objects
     */
    private void storeCollections(Persister persister) {
        persister.storeAll(isbn13ToBook, authorToBooks, genreToBooks, publisherToBooks, languageToBooks);
    }

    /**
     * Adds a book to all collections used by this implementation.
     *
     * @param book the book to add
     */
    private void addToCollections(Book book) {
        isbn13ToBook.put(book.isbn13(), book);
        addToMap(authorToBooks, book.author(), book);
        addToMap(genreToBooks, book.genre(), book);
        addToMap(publisherToBooks, book.publisher(), book);
        addToMap(languageToBooks, book.language(), book);
    }

    /**
     * Adds a book to a map with a list as values.
     * If no list is present for the given key, it will be created.
     *
     * @param <K>  the key type
     * @param map  the collection
     * @param key  the key
     * @param book the book to add
     */
    private <K> void addToMap(Map<K, List<Book>> map, K key, Book book) {
        map.computeIfAbsent(key, k -> new ArrayList<>(1024)).add(book);
    }

    /**
     * Gets all books as a sorted {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all books
     */
    public List<Book> all() {
        return read(() -> isbn13ToBook.values().stream().sorted().collect(toList()));
    }

    /**
     * Gets all authors as a sorted {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all authors
     */
    public List<Author> authors() {
        return read(() -> authorToBooks.keySet().stream().sorted().collect(toList()));
    }

    /**
     * Gets all genres as a sorted {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all genres
     */
    public List<Genre> genres() {
        return read(() -> genreToBooks.keySet().stream().sorted().collect(toList()));
    }

    /**
     * Gets all publishers as a sorted {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all publishers
     */
    public List<Publisher> publishers() {
        return read(() -> publisherToBooks.keySet().stream().sorted().collect(toList()));
    }

    /**
     * Gets all languages as a sorted {@link List}.
     * Modifications to the returned list are not reflected to the backed data.
     *
     * @return all languages
     */
    public List<Language> languages() {
        return read(() -> languageToBooks.keySet().stream().sorted().collect(toList()));
    }

    /**
     * Gets the total amount of all books.
     *
     * @return the amount of books
     */
    public int bookCount() {
        return read(isbn13ToBook::size);
    }

    /**
     * Executes a function with a {@link Stream} of {@link Book}s and returns the computed value.
     * <p>
     * This example counts all authors which have written a book with a title starting with 'The':
     * <pre>
     * long bookCount = compute(books ->
     *    books.filter(book -> book.title().startsWith("The"))
     *        .map(Book::author)
     *        .distinct()
     *        .count()
     * );
     * </pre>
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T compute(Function<Stream<Book>, T> streamFunction) {
        return read(() -> streamFunction.apply(isbn13ToBook.values().stream()));
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Book}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param author         author to filter by
     * @param streamFunction computing function
     * @return the computed result
     * @see #compute(Function)
     */
    public <T> T computeByAuthor(final Author author, final Function<Stream<Book>, T> streamFunction) {
        return read(() -> {
            List<Book> list = authorToBooks.get(author);
            return streamFunction.apply(list != null ? list.stream() : Stream.empty());
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Book}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param genre          genre to filter by
     * @param streamFunction computing function
     * @return the computed result
     * @see #compute(Function)
     */
    public <T> T computeByGenre(Genre genre, Function<Stream<Book>, T> streamFunction) {
        return read(() -> {
            List<Book> list = genreToBooks.get(genre);
            return streamFunction.apply(list != null ? list.stream() : Stream.empty());
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Book}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param publisher      publisher to filter by
     * @param streamFunction computing function
     * @return the computed result
     * @see #compute(Function)
     */
    public <T> T computeByPublisher(Publisher publisher, Function<Stream<Book>, T> streamFunction) {
        return read(() -> {
            List<Book> list = publisherToBooks.get(publisher);
            return streamFunction.apply(list != null ? list.stream() : Stream.empty());
        });
    }

    /**
     * Executes a function with a pre-filtered {@link Stream} of {@link Book}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param language       language to filter by
     * @param streamFunction computing function
     * @return the computed result
     * @see #compute(Function)
     */
    public <T> T computeByLanguage(Language language, Function<Stream<Book>, T> streamFunction) {
        return read(() -> {
            List<Book> list = languageToBooks.get(language);
            return streamFunction.apply(list != null ? list.stream() : Stream.empty());
        });
    }

    /**
     * Gets the book with a specific ISBN or <code>null</code> if none was found.
     *
     * @param isbn13 the ISBN to search by
     * @return the matching book or <code>null</code>
     */
    public Book ofIsbn13(String isbn13) {
        return read(() -> isbn13ToBook.get(isbn13));
    }

    /**
     * Executes a function with a {@link Stream} of {@link Genre}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeGenres(Function<Stream<Genre>, T> streamFunction) {
        return read(() -> streamFunction.apply(genreToBooks.keySet().stream()));
    }

    /**
     * Executes a function with a {@link Stream} of {@link Author}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeAuthors(Function<Stream<Author>, T> streamFunction) {
        return read(() -> streamFunction.apply(authorToBooks.keySet().stream()));
    }

    /**
     * Executes a function with a {@link Stream} of {@link Publisher}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computePublishers(Function<Stream<Publisher>, T> streamFunction) {
        return read(() -> streamFunction.apply(publisherToBooks.keySet().stream()));
    }

    /**
     * Executes a function with a {@link Stream} of {@link Language}s and returns the computed value.
     *
     * @param <T>            the return type
     * @param streamFunction computing function
     * @return the computed result
     */
    public <T> T computeLanguages(Function<Stream<Language>, T> streamFunction) {
        return read(() -> streamFunction.apply(languageToBooks.keySet().stream()));
    }

    /**
     * Gets all books written by a specific author.
     *
     * @param author the author to search for
     * @return a list of books
     */
    public List<Book> allByAuthor(Author author) {
        return computeByAuthor(author, books -> books.collect(toList()));
    }

    /**
     * Gets all books with a specific genre.
     *
     * @param genre the genre to search for
     * @return a list of books
     */
    public List<Book> allByGenre(Genre genre) {
        return computeByGenre(genre, books -> books.collect(toList()));
    }

    /**
     * Gets all books of a specific publisher.
     *
     * @param publisher the publisher to search for
     * @return a list of books
     */
    public List<Book> allByPublisher(Publisher publisher) {
        return computeByPublisher(publisher, books -> books.collect(toList()));
    }

    /**
     * Gets all books which are published in a specific language.
     *
     * @param language the language to search for
     * @return a list of books
     */
    public List<Book> allByLanguage(Language language) {
        return computeByLanguage(language, books -> books.collect(toList()));
    }

}
