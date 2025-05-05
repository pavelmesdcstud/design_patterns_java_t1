package lt.esdc.shapes.repository.spec;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.repository.Specification;

/**
 * Checks if the first point of the shape lies strictly within the first quadrant (x > 0, y > 0).
 */
public class FirstQuadrantSpecification implements Specification<Shape> {

  @Override
  public boolean specified(Shape item) {
    if (item == null || item.points().isEmpty()) {
      return false;
    }
    Point firstPoint = item.points().get(0);
    return firstPoint.x() > 0 && firstPoint.y() > 0;
  }
}
