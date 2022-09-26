package be.rubus.microstream.training.exercise.cars;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@ApplicationPath("/cars")
@ApplicationScoped
public class CarsApplication extends Application {
}
