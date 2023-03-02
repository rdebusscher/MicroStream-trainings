package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "LANGUAGE")
public class LanguageEntity extends BaseEntity {
    @Column(name = "LANGUAGETAG")
    private String languageTag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
    private List<BookEntity> books = new ArrayList<>();

    public LanguageEntity() {
        super();
    }


    public String getLanguageTag() {
        return languageTag;
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }

    public Locale toLocale() {
        return Locale.forLanguageTag(languageTag);
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public BookEntity addBook(BookEntity book) {
        this.getBooks().add(book);
        book.setLanguage(this);
        return book;
    }

    public BookEntity removeBook(BookEntity book) {
        this.getBooks().remove(book);
        book.setLanguage(null);
        return book;
    }
}
