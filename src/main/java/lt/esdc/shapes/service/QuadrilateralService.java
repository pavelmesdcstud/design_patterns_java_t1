package lt.esdc.shapes.service;

import java.util.List;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Quadrilateral;

public class QuadrilateralService {

  private final PointService pointService; // todo: not sure if that's correct

  public QuadrilateralService(PointService pointService) {
    this.pointService = pointService;
  }

  private static final double EPSILON = 1e-9;

  public double calculatePerimeter(Quadrilateral quad) {
    List<Point> p = quad.points();
    return pointService.distance(p.get(0), p.get(1)) + pointService.distance(p.get(1), p.get(2))
        + pointService.distance(p.get(2), p.get(3)) + pointService.distance(p.get(3), p.get(0));
  }

  public double calculateArea(Quadrilateral quad) {
    Point p1 = quad.getP1();
    Point p2 = quad.getP2();
    Point p3 = quad.getP3();
    Point p4 = quad.getP4();
    return 0.5 * Math.abs(
        p1.x() * p2.y() + p2.x() * p3.y() + p3.x() * p4.y() + p4.x() * p1.y() - p2.x() * p1.y()
            + p3.x() * p2.y() - p4.x() * p3.y() - p1.x() * p4.y());
  }

  public boolean isValid(Quadrilateral quad) {
    return !hasCollinearPoints(quad);
  }

  private boolean hasCollinearPoints(Quadrilateral quad) {
    List<Point> p = quad.points();
    return pointService.areCollinear(p.get(0), p.get(1), p.get(2)) || pointService.areCollinear(
        p.get(0), p.get(1), p.get(3)) || pointService.areCollinear(p.get(0), p.get(2), p.get(3))
        || pointService.areCollinear(p.get(1), p.get(2), p.get(3));
  }

  /**
   * Checks if the quadrilateral is convex. Assumes points are ordered sequentially (e.g., clockwise
   * or counter-clockwise). Uses the cross product sign consistency check.
   *
   * @param quad The quadrilateral.
   * @return true if convex, false otherwise.
   */
  public boolean isConvex(Quadrilateral quad) {
    List<Point> p = quad.points();
    if (p.size() < 4) {
      return false;
    }

    Boolean expectedSign = null;

    for (int i = 0; i < 4; i++) {
      Point p1 = p.get(i);
      Point p2 = p.get((i + 1) % 4);
      Point p3 = p.get((i + 2) % 4);

      double crossProduct = pointService.crossProductZ(p1, p2, p3);

      if (Math.abs(crossProduct) < EPSILON) {
        return false;
      }

      boolean currentSign = crossProduct > 0;
      if (expectedSign == null) {
        expectedSign = currentSign;
      } else if (expectedSign != currentSign) {
        return false; // Sign changed, indicates concave vertex
      }
    }
    return true;
  }


  /**
   * Checks if the quadrilateral is a rhombus. Condition: All four sides are equal in length.
   *
   * @param quad The quadrilateral.
   * @return true if it's a rhombus.
   */
  public boolean isRhombus(Quadrilateral quad) {
    if (!isValid(quad)) {
      return false;
    }
    List<Point> p = quad.points();
    double s12 = pointService.squaredDistance(p.get(0), p.get(1));
    double s23 = pointService.squaredDistance(p.get(1), p.get(2));
    double s34 = pointService.squaredDistance(p.get(2), p.get(3));
    double s41 = pointService.squaredDistance(p.get(3), p.get(0));

    // Check if all sides are equal (within tolerance)
    return areClose(s12, s23) && areClose(s23, s34) && areClose(s34, s41);
  }

  /**
   * Checks if the quadrilateral is a square. Conditions: All sides equal, and diagonals equal. (Or
   * all sides equal & one right angle). Assumes points are P1, P2, P3, P4 sequentially.
   *
   * @param quad The quadrilateral.
   * @return true if it's a square.
   */
  public boolean isSquare(Quadrilateral quad) {
    if (!this.isValid(quad)) {
      return false;
    }
    List<Point> p = quad.points();
    if (!isRhombus(quad)) {
      return false;
    }
    double d13 = pointService.squaredDistance(p.get(0), p.get(2));
    double d24 = pointService.squaredDistance(p.get(1), p.get(3));
    return areClose(d13, d24);
  }

  /**
   * Checks if the quadrilateral is a rectangle. Condition: Diagonals are equal and bisect each
   * other (midpoints coincide). Or: Opposite sides equal and diagonals equal.
   *
   * @param quad The quadrilateral.
   * @return true if it's a rectangle.
   */
  public boolean isRectangle(Quadrilateral quad) {
    if (!isValid(quad)) {
      return false;
    }
    List<Point> p = quad.points();
    double s12 = pointService.squaredDistance(p.get(0), p.get(1));
    double s23 = pointService.squaredDistance(p.get(1), p.get(2));
    double s34 = pointService.squaredDistance(p.get(2), p.get(3));
    double s41 = pointService.squaredDistance(p.get(3), p.get(0));
    double d13 = pointService.squaredDistance(p.get(0), p.get(2));
    double d24 = pointService.squaredDistance(p.get(1), p.get(3));

    // Check opposite sides equal AND diagonals equal
    return areClose(s12, s34) && areClose(s23, s41) && areClose(d13, d24);
  }


  /**
   * Checks if the quadrilateral is a trapezoid. Condition: At least one pair of opposite sides is
   * parallel.
   *
   * @param quad The quadrilateral.
   * @return true if it's a trapezoid.
   */
  public boolean isTrapezoid(Quadrilateral quad) {
    if (!isValid(quad)) {
      return false;
    }
    List<Point> p = quad.points();
    // Check parallelism using slopes or cross products (cross product is more robust)
    // Vector AB = p1->p2, Vector BC = p2->p3, Vector CD = p3->p4, Vector DA = p4->p1
    Point p1 = p.get(0), p2 = p.get(1), p3 = p.get(2), p4 = p.get(3);

    // Check if AB is parallel to CD
    boolean ab_cd_parallel = areParallel(p1, p2, p3, p4);
    // Check if BC is parallel to DA
    boolean bc_da_parallel = areParallel(p2, p3, p4, p1);

    // It's a trapezoid if at least one pair is parallel
    // Note: Parallelograms (including squares, rectangles, rhombuses) are also trapezoids by this definition.
    return ab_cd_parallel || bc_da_parallel;
  }

  private boolean areClose(double a, double b) {
    return Math.abs(a - b) < EPSILON;
  }

  private boolean areParallel(Point p1, Point p2, Point p3, Point p4) {
    double v1x = p2.x() - p1.x();
    double v1y = p2.y() - p1.y();
    double v2x = p4.x() - p3.x();
    double v2y = p4.y() - p3.y();

    // Z component of cross product V1 x V2
    double crossProduct = v1x * v2y - v1y * v2x;

    return Math.abs(crossProduct) < EPSILON;
  }
}
