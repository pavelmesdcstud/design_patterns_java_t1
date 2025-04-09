package lt.esdc.shapes;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import lt.esdc.shapes.entity.Quadrilateral;
import lt.esdc.shapes.io.QuadrilateralStringParser;
import lt.esdc.shapes.io.ShapeFileReader;
import lt.esdc.shapes.service.PointService;
import lt.esdc.shapes.service.QuadrilateralService;

public class Main {

  public static void main(String[] args) throws URISyntaxException {
    ClassLoader classLoader = Main.class.getClassLoader();
    Path p = Paths.get(Objects.requireNonNull(classLoader.getResource("data.txt")).toURI());
    ShapeFileReader<Quadrilateral> shapeFileReader = new ShapeFileReader<>(p,
        new QuadrilateralStringParser());
    QuadrilateralService service = new QuadrilateralService(new PointService());
    shapeFileReader.readShapes().iterator().forEachRemaining(shape -> {
      System.out.println("Shape ID: " + shape.id());
      System.out.println("Points: " + shape.points());
      System.out.println("Area: " + service.calculateArea(shape));
      System.out.println("========\n");
    });
  }

}
