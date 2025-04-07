package lt.esdc.shapes.validator;

import java.util.List;
import java.util.function.Predicate;
import lt.esdc.shapes.entity.Point;

public interface ShapeDataValidator extends Predicate<List<Point>> {

}
