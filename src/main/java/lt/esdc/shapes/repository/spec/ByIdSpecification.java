package lt.esdc.shapes.repository.spec;

import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.repository.Specification;

public class ByIdSpecification implements Specification<Shape> {
  private final String id;

  public ByIdSpecification(String id) {
    this.id = id;
  }

  @Override
  public boolean specified(Shape item) {
    return item != null && item.id().equals(id);
  }
}
