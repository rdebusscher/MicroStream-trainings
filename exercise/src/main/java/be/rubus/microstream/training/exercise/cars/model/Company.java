package be.rubus.microstream.training.exercise.cars.model;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private String name;
    private List<Brand> brands = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
