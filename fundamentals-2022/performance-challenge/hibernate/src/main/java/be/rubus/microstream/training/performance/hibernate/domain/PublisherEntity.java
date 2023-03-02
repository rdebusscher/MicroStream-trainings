package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PUBLISHER")
public class PublisherEntity extends NamedWithAddressEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
    private List<BookEntity> books = new ArrayList<>();

    public PublisherEntity() {
        super();
    }

    public PublisherEntity(Long id, String name, AddressEntity address) {
        super(name, address);
        setId(id);
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }


}
