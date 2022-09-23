package be.rubus.microstream.training.crud.storage;

import be.rubus.microstream.training.crud.domain.Book;

import java.util.ArrayList;
import java.util.List;


public class DataRoot {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

}
