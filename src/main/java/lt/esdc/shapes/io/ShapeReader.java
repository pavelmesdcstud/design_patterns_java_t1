package lt.esdc.shapes.io;

import java.util.List;
import lt.esdc.shapes.entity.Shape;

public interface ShapeReader<S extends Shape> {

  List<S> readShapes();
}
