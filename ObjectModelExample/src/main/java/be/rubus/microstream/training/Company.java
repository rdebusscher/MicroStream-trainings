package be.rubus.microstream.training;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Company {
	private String id = UUID.randomUUID().toString();

	private List<Store> stores = new ArrayList<>();
	private List<Customer> customers = new ArrayList<>();
	private Address address;

}
