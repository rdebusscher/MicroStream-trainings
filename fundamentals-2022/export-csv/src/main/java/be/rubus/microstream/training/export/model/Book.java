package be.rubus.microstream.training.export.model;

import java.util.StringJoiner;

public class Book {

    private final String isbn;
    private final String name;
    private final String author;
    private final int year;

    public Book(String isbn, String name, String author, int year) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]\n")
                .add("isbn='" + isbn + "'")
                .add("name='" + name + "'")
                .add("author='" + author + "'")
                .add("year=" + year)
                .toString();
    }
}
