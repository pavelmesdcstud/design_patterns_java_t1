package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Point;

public class PointService {

  private static final double EPSILON = 1e-9;

  public double squaredDistance(Point p1, Point p2) {
    double dx = p1.x() - p2.x();
    double dy = p1.y() - p2.y();
    return dx * dx + dy * dy;
  }

  public double distance(Point p1, Point p2) {
    return Math.sqrt(squaredDistance(p1, p2));
  }

  public boolean areCollinear(Point p1, Point p2, Point p3) {
    double area = (p1.x() * (p2.y() - p3.y()) +
        p2.x() * (p3.y() - p1.y()) +
        p3.x() * (p1.y() - p2.y()));
    return Math.abs(area) < EPSILON;
  }

  public double crossProductZ(Point p1, Point p2, Point p3) {
    return (p2.x() - p1.x()) * (p3.y() - p1.y()) - (p2.y() - p1.y()) * (p3.x() - p1.x());
  }
}
