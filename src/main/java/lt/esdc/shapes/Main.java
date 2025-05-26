// ./lt/esdc/shapes/Main.java
package lt.esdc.shapes;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Quadrilateral;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.parser.QuadrilateralStringParser;
import lt.esdc.shapes.io.ShapeFileReader;
import lt.esdc.shapes.repository.Repository;
import lt.esdc.shapes.repository.spec.ByAreaRangeSpecification;
import lt.esdc.shapes.repository.spec.FirstQuadrantSpecification;
import lt.esdc.shapes.repository.spec.IsSquareSpecification;
import lt.esdc.shapes.service.PointService;
import lt.esdc.shapes.service.QuadrilateralService;
import lt.esdc.shapes.warehouse.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws URISyntaxException {
    logger.info("Application starting...");

    ClassLoader classLoader = Main.class.getClassLoader();
    Path p = Paths.get(Objects.requireNonNull(classLoader.getResource("data.txt")).toURI());
    ShapeFileReader<Quadrilateral> shapeFileReader = new ShapeFileReader<>(p,
        new QuadrilateralStringParser());

    Repository repository = Repository.getInstance();
    Warehouse warehouse = Warehouse.getInstance();

    // --- Reading and Loading Shapes ---
    List<Quadrilateral> loadedShapes = shapeFileReader.readShapes();
    logger.info("Loaded {} shapes from file.", loadedShapes.size());

    repository.addAll(loadedShapes);
    logger.info("Added {} shapes to the repository.", repository.getAll().size());

    // --- Demonstrate Warehouse ---
    logger.info("--- Initial Warehouse Parameters ---");
    repository.getAll().forEach(shape -> {
      warehouse.getParameters(shape.id())
          .ifPresentOrElse(
              params -> logger.info("Shape ID: {}, Area: {}, Perimeter: {}",
                  shape.id(), params.area(), params.perimeter()),
              () -> logger.warn("Shape ID: {} - Parameters not found", shape.id())
          );
    });

    // --- Demonstrate Observer ---
    logger.info("--- Demonstrating Observer on Parameter Change ---");
    if (!repository.getAll().isEmpty() && repository.getAll()
        .getFirst() instanceof Quadrilateral firstQuad) {
      String idToModify = firstQuad.id();
      logger.info("Modifying shape ID: {}", idToModify);
      warehouse.getParameters(idToModify).ifPresent(params ->
          logger.debug("BEFORE Change - ID: {}, Area: {}, Perimeter: {}",
              idToModify, params.area(), params.perimeter()));
      List<Point> currentPoints = firstQuad.points();
      List<Point> newPoints = List.of(
          new Point(currentPoints.get(0).x() + 10, currentPoints.get(0).y() + 10),
          // Shift first point
          currentPoints.get(1),
          currentPoints.get(2),
          currentPoints.get(3)
      );
      try {
        firstQuad.setPoints(newPoints);
        logger.trace("Called setPoints() on {}", idToModify);
        warehouse.getParameters(idToModify).ifPresent(params ->
            logger.debug("AFTER Change  - ID: {}, Area: {}, Perimeter: {}",
                idToModify, params.area(), params.perimeter()));

      } catch (IllegalArgumentException e) {
        logger.error("Failed to set new points for shape {}: {}", idToModify, e.getMessage());
        warehouse.getParameters(idToModify).ifPresent(params ->
            logger.warn("AFTER FAILED Change - ID: {}, Area: {}, Perimeter: {}",
                idToModify, params.area(), params.perimeter()));
      }


    } else {
      logger.warn("No Quadrilateral found in repository to demonstrate modification.");
    }

    logger.info("--- Querying with Specifications ---");

    // 1. By Area Range
    ByAreaRangeSpecification areaSpec = new ByAreaRangeSpecification(5.0, 50.0);
    List<Shape> shapesInAreaRange = repository.query(areaSpec);
    logger.info("Shapes with area between 5.0 and 50.0: {}", shapesInAreaRange.size());
    shapesInAreaRange.forEach(s -> logger.debug("  - {} (Area: {})", s.id(),
        warehouse.getArea(s.id())));

    // 2. Square shapes
    IsSquareSpecification squareSpec = new IsSquareSpecification(
        new QuadrilateralService(new PointService()));
    List<Shape> squareShapes = repository.query(squareSpec);
    logger.info("Square shapes: {}", squareShapes.size());
    squareShapes.forEach(s -> logger.debug("  - {}", s.id()));

    // 3. First Point in First Quadrant
    FirstQuadrantSpecification firstQuadSpec = new FirstQuadrantSpecification();
    List<Shape> firstQuadShapes = repository.query(firstQuadSpec);
    logger.info("Shapes with first point in Quadrant I (x>0, y>0): {}", firstQuadShapes.size());
    firstQuadShapes.forEach(s -> logger.debug("  - {} (P1: {})", s.id(), s.points().getFirst()));

    // --- Demonstrate Sorting ---
    logger.info("--- Sorting Repository ---");

    logger.info("Shapes before sorting:");
    repository.getAll()
        .forEach(s -> logger.debug("  - ID: {}, Area: {}", s.id(), warehouse.getArea(s.id())));

    repository.sortByArea(); // Sort by area
    logger.info("Shapes after sorting by Area:");
    repository.getAll()
        .forEach(s -> logger.debug("  - ID: {}, Area: {}", s.id(), warehouse.getArea(s.id())));

    repository.sortById(); // Sort by ID
    logger.info("Shapes after sorting by ID:");
    repository.getAll().forEach(s -> logger.debug("  - ID: {}", s.id()));

    logger.info("Application finished.");
  }
}
