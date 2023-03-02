package be.rubus.microstream.training.lazy.database;


import be.rubus.microstream.training.lazy.model.LazyPhoto;

import java.util.ArrayList;
import java.util.List;

public class LazyRoot {

    private final List<LazyPhoto> photos = new ArrayList<>();

    public List<LazyPhoto> getPhotos() {
        return photos;
    }

    public void addPhoto(LazyPhoto photo) {
        photos.add(photo);
    }
}
