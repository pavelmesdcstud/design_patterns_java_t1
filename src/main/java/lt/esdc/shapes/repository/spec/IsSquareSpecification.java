package lt.esdc.shapes.repository.spec;

import lt.esdc.shapes.entity.Quadrilateral;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.repository.Specification;
import lt.esdc.shapes.service.QuadrilateralService;

public class IsSquareSpecification implements Specification<Shape> {

  private final QuadrilateralService quadService;

  public IsSquareSpecification(QuadrilateralService quadService) {
    this.quadService = quadService;
  }

  @Override
  public boolean specified(Shape item) {
    if (item instanceof Quadrilateral quad) {
      // Handle potential exceptions if service methods throw them for invalid quads
      try {
        return quadService.isSquare(quad);
      } catch (Exception e) {
        return false; // Treat invalid shapes as not matching
      }
    }
    return false; // Only applies to Quadrilaterals
  }
}
