package be.rubus.microstream.training.performance.model.builders;

import be.rubus.microstream.training.performance.model.*;
import be.rubus.microstream.training.performance.utils.MoneyUtil;

import javax.money.MonetaryAmount;

public class BookBuilder {
    private Long id;
    private String isbn13;
    private String title;
    private Author author;
    private Genre genre;
    private Publisher publisher;
    private Language language;
    private MonetaryAmount purchasePrice;
    private MonetaryAmount retailPrice;

    public BookBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BookBuilder withIsbn13(String isbn13) {
        this.isbn13 = isbn13;
        return this;
    }

    public BookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookBuilder withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public BookBuilder withPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder withLanguage(Language language) {
        this.language = language;
        return this;
    }

    public BookBuilder withPurchasePrice(MonetaryAmount purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    public BookBuilder withPurchasePrice(double purchasePrice) {
        this.purchasePrice = MoneyUtil.money(purchasePrice);
        return this;
    }

    public BookBuilder withRetailPrice(MonetaryAmount retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    public BookBuilder withRetailPrice(double retailPrice) {
        this.retailPrice = MoneyUtil.money(retailPrice);
        return this;
    }

    public Book build() {
        return new Book(id, isbn13, title, author, genre, publisher, language, purchasePrice, retailPrice);
    }
}