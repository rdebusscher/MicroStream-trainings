package be.rubus.microstream.training.performance.model;

import javax.money.MonetaryAmount;

/**
 * Book entity which holds an ISBN-13, title, {@link Author}, {@link Genre}, {@link Publisher},
 * {@link Language} and the purchase and retail price.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Book extends Named {
    private final String isbn13;
    private final Author author;
    private final Genre genre;
    private final Publisher publisher;
    private final Language language;
    private final MonetaryAmount purchasePrice;
    private final MonetaryAmount retailPrice;

    /**
     * Constructor to create a new {@link Book} instance.
     *
     * @param isbn13        a valid ISBN-13
     * @param title         not empty
     * @param author        not <code>null</code>
     * @param genre         not <code>null</code>
     * @param publisher     not <code>null</code>
     * @param language      not <code>null</code>
     * @param purchasePrice valid price
     * @param retailPrice   valid price
     */

    public Book(Long id, String isbn13, String title, Author author, Genre genre, Publisher publisher, Language language, MonetaryAmount purchasePrice, MonetaryAmount retailPrice) {
        super(id, title);

        this.isbn13 = isbn13;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.language = language;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
    }

    /**
     * Get the ISBN-13.
     *
     * @return the ISBN-13
     */
    public String isbn13() {
        return isbn13;
    }

    /**
     * Get the title.
     *
     * @return the title
     */
    public String title() {
        return name();
    }

    /**
     * Get the author.
     *
     * @return the author
     */
    public Author author() {
        return author;
    }

    /**
     * Get the genre.
     *
     * @return the genre
     */
    public Genre genre() {
        return genre;
    }

    /**
     * Get the publisher.
     *
     * @return the publisher
     */
    public Publisher publisher() {
        return publisher;
    }

    /**
     * Get the language.
     *
     * @return the language
     */
    public Language language() {
        return language;
    }

    /**
     * Get the purchase price.
     *
     * @return the purchase price
     */
    public MonetaryAmount purchasePrice() {
        return purchasePrice;
    }

    /**
     * Get the retail price.
     *
     * @return the retail price
     */
    public MonetaryAmount retailPrice() {
        return retailPrice;
    }

    @Override
    public String toString() {
        return "Book" + " [isbn13=" + isbn13 + ", title=" + name() + ", author=" + author + ", genre=" + genre + ", publisher=" + publisher + ", language=" + language + ", purchasePrice=" + purchasePrice + ", retailPrice=" + retailPrice + "]";
    }

}
