package be.rubus.microstream.training.performance.generator.insert;

@FunctionalInterface
public interface EntityCreator<P,E> {

    E createEntity(P pojo);
}
