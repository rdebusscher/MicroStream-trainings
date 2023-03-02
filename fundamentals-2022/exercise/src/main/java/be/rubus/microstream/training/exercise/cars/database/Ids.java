package be.rubus.microstream.training.exercise.cars.database;

import be.rubus.microstream.training.exercise.cars.model.Car;

import java.util.concurrent.atomic.AtomicLong;

public final class Ids {

    private static AtomicLong CAR_ID;

    private Ids() {
        long maxCarId = DB.getRoot().getCars().stream().mapToLong(Car::getId).max().orElse(-1);

        CAR_ID = new AtomicLong(maxCarId + 1);
    }

    public static Long nextCarId() {
        return CAR_ID.getAndIncrement();
    }
}
