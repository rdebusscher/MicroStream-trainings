package be.rubus.microstream.training.exercise.cars.model;

public class Car {

    private Long id;
    private String model;
    private double price;
    private Brand brand;

    /**
     * For JSON handling
     */
    public Car() {
    }

    public Car(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
