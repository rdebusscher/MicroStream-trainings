package be.rubus.microstream.training.exercise.cars.model;

public class Brand {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Brand newBrand(String name) {
        Brand result = new Brand();
        result.setName(name);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brand)) {
            return false;
        }

        Brand brand = (Brand) o;

        return name.equals(brand.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
