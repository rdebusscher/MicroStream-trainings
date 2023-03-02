
package be.rubus.microstream.training.lazy.model;


import javax.json.bind.annotation.JsonbTransient;
import java.util.Objects;


public class Photo {
    private long id;
    private String description;

    @JsonbTransient
    private byte[] image;

    public Photo(long id, String description, byte[] image) {
        this.id = id;
        this.description = description;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
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
                ", imageSize="
                + (this.image == null ? 0 : this.image.length)
                +
                '}';
    }
}
