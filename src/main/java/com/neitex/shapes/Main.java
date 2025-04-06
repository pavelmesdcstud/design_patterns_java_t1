package com.neitex.shapes;

import com.neitex.shapes.entity.Quadrilateral;
import com.neitex.shapes.io.QuadrilateralStringReader;
import com.neitex.shapes.io.ShapeFileReader;
import com.neitex.shapes.service.PointService;
import com.neitex.shapes.service.QuadrilateralService;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {

  public static void main(String[] args) throws URISyntaxException {
    ClassLoader classLoader = Main.class.getClassLoader();
    Path p = Paths.get(Objects.requireNonNull(classLoader.getResource("data.txt")).toURI());
    ShapeFileReader<Quadrilateral> shapeFileReader = new ShapeFileReader<>(p,
        new QuadrilateralStringReader());
    QuadrilateralService service = new QuadrilateralService(new PointService());
    shapeFileReader.readShapes().iterator().forEachRemaining(shape -> {
      System.out.println("Shape ID: " + shape.id());
      System.out.println("Points: " + shape.points());
      System.out.println("Serialized: " + shape.serialize());
      System.out.println("Area: " + service.calculateArea(shape));
      System.out.println("========\n");
    });
  }

}
