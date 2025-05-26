package lt.esdc.shapes.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lt.esdc.shapes.entity.Quadrilateral;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.observer.ShapeObserver;
import lt.esdc.shapes.service.PointService;
import lt.esdc.shapes.service.QuadrilateralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Warehouse implements ShapeObserver {

  private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);
  private static Warehouse instance;
  private final Map<String, ShapeParameters> parametersCache;
  private final QuadrilateralService quadrilateralService; // Needs service for calculations

  private Warehouse() {
    parametersCache = new HashMap<>();
    this.quadrilateralService = new QuadrilateralService(new PointService());
    logger.info("Warehouse initialized.");
  }

  public static Warehouse getInstance() {
    if (instance == null) {
      instance = new Warehouse();
    }
    return instance;
  }

  /**
   * Retrieves parameters for a given shape ID.
   *
   * @param shapeId The ID of the shape.
   * @return An Optional containing the parameters if found, empty otherwise.
   */
  public Optional<ShapeParameters> getParameters(String shapeId) {
    return Optional.ofNullable(parametersCache.get(shapeId));
  }

  /**
   * Initial calculation and storage of parameters when a shape is first added.
   *
   * @param shape The shape to register.
   */
  public void registerShape(Shape shape) {
    if (shape instanceof Quadrilateral quad) {
      calculateAndStoreParameters(quad);
      logger.debug("Registered parameters for shape ID: {}", shape.id());
    } else {
      logger.warn("Warehouse currently only supports Quadrilateral parameters. Shape ID: {}",
          shape.id());
      throw new IllegalArgumentException("Shape must be a Quadrilateral parameter");
    }
  }

  /**
   * Removes parameters for a given shape ID.
   *
   * @param shapeId The ID of the shape.
   */
  public void removeShape(String shapeId) {
    if (parametersCache.remove(shapeId) != null) {
      logger.debug("Removed parameters for shape ID: {}", shapeId);
    }
  }


  /**
   * Observer method called when a shape changes. Recalculates and updates parameters in the cache.
   *
   * @param shape The shape that has changed.
   */
  @Override
  public void update(Shape shape) {
    if (shape instanceof Quadrilateral quad) {
      calculateAndStoreParameters(quad);
      logger.info("Updated parameters for shape ID: {}", shape.id());
    } else {
      logger.warn("Received update for unsupported shape type. Shape ID: {}", shape.id());
    }
  }

  private void calculateAndStoreParameters(Quadrilateral quad) {
    try {
      double area = quadrilateralService.calculateArea(quad);
      double perimeter = quadrilateralService.calculatePerimeter(quad);
      ShapeParameters params = new ShapeParameters(area, perimeter);
      parametersCache.put(quad.id(), params);
    } catch (Exception e) {
      logger.error("Failed to calculate parameters for shape ID: {}", quad.id(), e);
      parametersCache.remove(quad.id()); // Remove potentially stale data
    }
  }

  public double getArea(String shapeId) {
    return getParameters(shapeId).map(ShapeParameters::area).orElse(Double.NaN);
  }

  public double getPerimeter(String shapeId) {
    return getParameters(shapeId).map(ShapeParameters::perimeter).orElse(Double.NaN);
  }
}
