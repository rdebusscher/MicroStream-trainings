package be.rubus.microstream.training;

import java.util.UUID;


public class Book {
	private String id = UUID.randomUUID().toString();

	private Author author;
}
