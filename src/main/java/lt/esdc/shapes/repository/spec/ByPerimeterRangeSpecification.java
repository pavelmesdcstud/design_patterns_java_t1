package lt.esdc.shapes.repository.spec;

import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.repository.Specification;
import lt.esdc.shapes.warehouse.Warehouse;

public class ByPerimeterRangeSpecification implements Specification<Shape> {

  private final double minPerimeter;
  private final double maxPerimeter;
  private final Warehouse warehouse = Warehouse.getInstance();

  public ByPerimeterRangeSpecification(double minPerimeter, double maxPerimeter) {
    this.minPerimeter = minPerimeter;
    this.maxPerimeter = maxPerimeter;
  }

  @Override
  public boolean specified(Shape item) {
    if (item == null) {
      return false;
    }
    double perimeter = warehouse.getPerimeter(item.id());
    return !Double.isNaN(perimeter) && perimeter >= minPerimeter && perimeter <= maxPerimeter;
  }
}
