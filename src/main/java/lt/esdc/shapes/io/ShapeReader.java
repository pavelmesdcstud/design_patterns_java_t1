package lt.esdc.shapes.io;

import java.util.List;
import lt.esdc.shapes.entity.Shape;

public interface ShapeReader<T extends Shape> {

  List<T> readShapes();
}
