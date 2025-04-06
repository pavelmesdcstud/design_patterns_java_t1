package com.neitex.shapes.validator;

import com.neitex.shapes.entity.Point;
import com.neitex.shapes.service.PointService;
import java.util.List;

public class QuadrilateralDataValidator implements ShapeDataValidator {
  private final PointService pointService;

  public QuadrilateralDataValidator() {
    this.pointService = new PointService();
  }

  @Override
  public boolean test(List<Point> points) {
    if (points == null || points.size() != 4) {
      return false;
    }
    Point p1 = points.get(0);
    Point p2 = points.get(1);
    Point p3 = points.get(2);
    Point p4 = points.get(3);
    return !pointService.areCollinear(p1, p2, p3) && !pointService.areCollinear(p1, p2, p4)
        && !pointService.areCollinear(p1, p3, p4) && !pointService.areCollinear(p2, p3, p4);

  }
}
