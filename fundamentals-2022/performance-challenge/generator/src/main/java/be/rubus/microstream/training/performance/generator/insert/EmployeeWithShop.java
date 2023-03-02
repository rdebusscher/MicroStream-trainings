package be.rubus.microstream.training.performance.generator.insert;


import be.rubus.microstream.training.performance.microstream.database.model.Shop;
import be.rubus.microstream.training.performance.model.Employee;

/**
 * Since we don't have a reference from the Employee to the Shop, this object keep track of it.
 */
public class EmployeeWithShop  {

    private final Employee employee;
    private final Shop shop;

    public EmployeeWithShop(Employee employee, Shop shop) {

        this.employee = employee;
        this.shop = shop;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Shop getShop() {
        return shop;
    }
}
