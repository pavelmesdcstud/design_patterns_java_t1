package lt.esdc.shapes.factory;

import java.util.List;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Shape;

public interface  ShapeFactory<S extends Shape> {

  S create(List<Point> points);
}
