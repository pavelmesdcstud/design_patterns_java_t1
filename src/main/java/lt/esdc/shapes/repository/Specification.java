package lt.esdc.shapes.repository;

@FunctionalInterface // Good practice for single-method interfaces
public interface Specification<T> {

  boolean specified(T item);
}
