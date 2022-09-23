package be.rubus.microstream.training.crud.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Book {

    // Not thread safe !!!
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private String isbn;
    private String name;
    private LocalDate releaseDate;
    private BigDecimal price;
    private Author author;

    public Book() {
        super();
    }

    public Book(String isbn, String name, LocalDate releaseDate, BigDecimal price, Author author) {
        this.isbn = isbn;
        this.name = name;
        this.price = price;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    // get and Set authorId from JSON
    public Long getAuthorId() {
        if (author == null) {
            return null;
        }
        return author.getId();
    }

    public void setAuthorId(Long authorId) {
        if (author == null) {
            author = new Author();
        }
        author.setId(authorId);
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    // For JSON loading, not Thread safe!
    public void setRelease(String release) {
        this.releaseDate = LocalDate.parse(release, fmt);
    }

}
