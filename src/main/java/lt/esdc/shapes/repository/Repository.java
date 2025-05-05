// ./lt/esdc/shapes/repository/Repository.java
package lt.esdc.shapes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.observer.ObservableShape;
import lt.esdc.shapes.warehouse.Warehouse; // Import Warehouse
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Singleton Repository to store Shape objects.
 * (Non-thread-safe Singleton implementation)
 */
public class Repository {
  private static final Logger logger = LoggerFactory.getLogger(Repository.class);
  private static Repository instance;
  private final List<Shape> shapes;
  private final Warehouse warehouse; // Reference to Warehouse for observer removal and parameters

  // Private constructor
  private Repository() {
    shapes = new ArrayList<>();
    warehouse = Warehouse.getInstance(); // Get warehouse instance
    logger.info("Repository initialized.");
  }

  // Non-thread-safe getInstance()
  public static Repository getInstance() {
    if (instance == null) {
      instance = new Repository();
    }
    return instance;
  }

  /**
   * Adds a shape to the repository.
   * Also registers the shape with the Warehouse and adds the Warehouse as an observer.
   *
   * @param shape The shape to add.
   */
  public void add(Shape shape) {
    if (shape == null || contains(shape.id())) {
      logger.warn("Attempted to add null shape or shape with duplicate ID: {}", shape != null ? shape.id() : "null");
      return; // Or throw exception
    }
    shapes.add(shape);
    warehouse.registerShape(shape); // Initial parameter calculation
    if (shape instanceof ObservableShape observable) {
      observable.addObserver(warehouse); // Add warehouse as observer
    }
    logger.debug("Added shape ID: {}", shape.id());
  }

  /**
   * Adds multiple shapes to the repository.
   *
   * @param shapesToAdd Collection of shapes to add.
   */
  public void addAll(List<? extends Shape> shapesToAdd) {
    if (shapesToAdd != null) {
      shapesToAdd.forEach(this::add); // Use the single add method for consistency
    }
  }

  /**
   * Removes a shape from the repository.
   * Also removes parameters from the Warehouse and removes the Warehouse as an observer.
   *
   * @param shape The shape to remove.
   * @return true if the shape was removed, false otherwise.
   */
  public boolean remove(Shape shape) {
    if (shape == null) return false;
    return removeById(shape.id());
  }

  /**
   * Removes a shape by its ID.
   *
   * @param shapeId The ID of the shape to remove.
   * @return true if the shape was removed, false otherwise.
   */
  public boolean removeById(String shapeId) {
    Shape shapeToRemove = shapes.stream()
        .filter(s -> s.id().equals(shapeId))
        .findFirst()
        .orElse(null);

    if (shapeToRemove != null) {
      if (shapeToRemove instanceof ObservableShape observable) {
        observable.removeObserver(warehouse); // Remove observer first
      }
      warehouse.removeShape(shapeId); // Remove parameters from warehouse
      boolean removed = shapes.remove(shapeToRemove);
      if (removed) {
        logger.debug("Removed shape ID: {}", shapeId);
      }
      return removed;
    }
    return false;
  }

  /**
   * Retrieves all shapes in the repository.
   *
   * @return An unmodifiable list of all shapes.
   */
  public List<Shape> getAll() {
    return Collections.unmodifiableList(shapes);
  }

  /**
   * Checks if a shape with the given ID exists in the repository.
   * @param shapeId The shape ID.
   * @return true if exists, false otherwise.
   */
  public boolean contains(String shapeId) {
    return shapes.stream().anyMatch(s -> s.id().equals(shapeId));
  }

  /**
   * Queries shapes based on a Specification.
   *
   * @param specification The criteria for selection.
   * @return A list of shapes matching the specification.
   */
  public List<Shape> query(Specification<Shape> specification) {
    return shapes.stream()
        .filter(specification::specified)
        .collect(Collectors.toList());
  }


  public void sortById() {
    shapes.sort(Comparator.comparing(Shape::id));
    logger.debug("Sorted shapes by ID.");
  }

  public void sortByArea() {
    // Use Warehouse instance directly in comparator
    shapes.sort(Comparator.comparingDouble(shape -> warehouse.getArea(shape.id())));
    logger.debug("Sorted shapes by Area.");
  }

  public void sortByPerimeter() {
    // Use Warehouse instance directly in comparator
    shapes.sort(Comparator.comparingDouble(shape -> warehouse.getPerimeter(shape.id())));
    logger.debug("Sorted shapes by Perimeter.");
  }

  public void sortByFirstPointX() {
    shapes.sort(Comparator.comparingDouble(shape -> shape.points().isEmpty() ? Double.NaN : shape.points().get(0).x()));
    logger.debug("Sorted shapes by First Point X coordinate.");
  }

  public void sortByFirstPointY() {
    shapes.sort(Comparator.comparingDouble(shape -> shape.points().isEmpty() ? Double.NaN : shape.points().get(0).y()));
    logger.debug("Sorted shapes by First Point Y coordinate.");
  }

  public void sort(Comparator<Shape> comparator) {
    shapes.sort(comparator);
    logger.debug("Sorted shapes using custom comparator.");
  }
}
