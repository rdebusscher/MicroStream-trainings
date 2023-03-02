package be.rubus.microstream.training.lazy.database;


import be.rubus.microstream.training.lazy.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class Root {

    private final List<Photo> photos = new ArrayList<>();

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
}
