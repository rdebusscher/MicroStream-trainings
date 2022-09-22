package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GENRE")
public class GenreEntity extends NamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
    private List<BookEntity> books = new ArrayList<>();

    public GenreEntity() {
        super();
    }

    public GenreEntity(String name) {
        super(name);
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

}
