package be.rubus.microstream.training.performance.hibernate.domain;


import be.rubus.microstream.training.performance.hibernate.converter.MoneyConverter;

import javax.money.MonetaryAmount;
import javax.persistence.*;

@Entity
@Table(name = "BOOK")
public class BookEntity extends BaseEntity {
    @Column(name = "ISBN13")
    private String isbn13;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    private AuthorEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENRE_ID")
    private GenreEntity genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUBLISHER_ID")
    private PublisherEntity publisher;

    @Column(name = "PURCHASE_PRICE")
    @Convert(converter = MoneyConverter.class)
    private MonetaryAmount purchasePrice;

    @Column(name = "RETAIL_PRICE")
    @Convert(converter = MoneyConverter.class)
    private MonetaryAmount retailPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LANGUAGE_ID")
    private LanguageEntity language;

    public BookEntity() {
        super();
    }

    public BookEntity(long id, String isbn13, String title, AuthorEntity author, GenreEntity genre, PublisherEntity publisher, MonetaryAmount purchasePrice, MonetaryAmount retailPrice, LanguageEntity language) {
        this.isbn13 = isbn13;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
        this.language = language;
        setId(id);
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public MonetaryAmount getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(MonetaryAmount purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public MonetaryAmount getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(MonetaryAmount retailPrice) {
        this.retailPrice = retailPrice;
    }

    public LanguageEntity getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEntity language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "BookEntity [title=" + title + ", author=" + author + ", genre=" + genre + ", publisher=" + publisher + ", language=" + language + ", purchasePrice=" + purchasePrice + ", retailPrice=" + retailPrice + "]";
    }

}
