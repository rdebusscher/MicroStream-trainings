
package be.rubus.microstream.training.lazy.model;


import one.microstream.reference.Lazy;

import javax.json.bind.annotation.JsonbTransient;
import java.util.Objects;


public class LazyPhoto {
    private long id;
    private String description;

    @JsonbTransient
    private final Lazy<byte[]> image;

    public LazyPhoto(long id, String description, byte[] image) {
        this.id = id;
        this.description = description;
        this.image = Lazy.Reference(image);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonbTransient
    public byte[] getImageData() {
        return image.get();
    }

    @JsonbTransient
    public Lazy<byte[]> getImage() {
        return image;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        LazyPhoto photo = (LazyPhoto) o;
        return this.id == photo.id;
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
                ", description='"
                + this.description
                + '\''
                +
                '}';
    }
}
