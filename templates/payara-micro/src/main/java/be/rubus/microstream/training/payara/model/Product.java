
package be.rubus.microstream.training.payara.model;


import java.util.Objects;


public class Product {
    private long id;
    private String name;
    private String description;
    private int rating;

    // For JSON processing
    public Product() {
    }

    public Product(long id, String name, String description, int rating
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Product product = (Product) o;
        return this.id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{"
                +
                "id="
                + this.id
                +
                ", name='"
                + this.name
                + '\''
                +
                ", description='"
                + this.description
                + '\''
                +
                ", rating="
                + this.rating
                +
                '}';
    }
}
