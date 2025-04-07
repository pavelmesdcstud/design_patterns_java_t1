package lt.esdc.shapes.factory;

import java.util.List;
import java.util.UUID;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Quadrilateral;
import lt.esdc.shapes.exception.IllegalArgumentsException;
import lt.esdc.shapes.validator.QuadrilateralDataValidator;
import lt.esdc.shapes.validator.ShapeDataValidator;

public class QuadrilateralFactory implements ShapeFactory<Quadrilateral> {
  private final ShapeDataValidator shapeDataValidator = new QuadrilateralDataValidator();

  @Override
  public Quadrilateral create(List<Point> points) {
    if (!shapeDataValidator.test(points)) {
      throw new IllegalArgumentsException("Invalid points for a quadrilateral.");
    }
    return new Quadrilateral(UUID.randomUUID().toString(), points);
  }
}
