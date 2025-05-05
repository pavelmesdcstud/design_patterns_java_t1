package lt.esdc.shapes.repository.spec;


import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.repository.Specification;
import lt.esdc.shapes.warehouse.Warehouse;

public class ByAreaRangeSpecification implements Specification<Shape> {

  private final double minArea;
  private final double maxArea;
  private final Warehouse warehouse = Warehouse.getInstance(); // Get Warehouse instance

  public ByAreaRangeSpecification(double minArea, double maxArea) {
    this.minArea = minArea;
    this.maxArea = maxArea;
  }

  @Override
  public boolean specified(Shape item) {
    if (item == null) {
      return false;
    }
    double area = warehouse.getArea(item.id());
    return !Double.isNaN(area) && area >= minArea && area <= maxArea;
  }
}
