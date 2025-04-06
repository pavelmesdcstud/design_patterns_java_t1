package com.neitex.shapes.factory;

import com.neitex.shapes.entity.Point;
import com.neitex.shapes.entity.Quadrilateral;
import com.neitex.shapes.exception.IllegalArgumentsException;
import com.neitex.shapes.validator.QuadrilateralDataValidator;
import com.neitex.shapes.validator.ShapeDataValidator;
import java.util.List;
import java.util.UUID;

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
