package lt.esdc.shapes.entity;

import java.util.List;
import java.util.Objects;

public record Quadrilateral(String id, List<Point> points) implements Shape {

  public Quadrilateral(String id, List<Point> points) {
    if (points == null || points.size() != 4) {
      throw new IllegalArgumentException("Quadrilateral must have exactly 4 points.");
    }
    this.points = List.copyOf(points);
    this.id = Objects.requireNonNull(id, "ID cannot be null");
  }

  public Point getP1() {
    return points.getFirst();
  }

  public Point getP2() {
    return points.get(1);
  }

  public Point getP3() {
    return points.get(2);
  }

  public Point getP4() {
    return points.get(3);
  }


  @Override
  public String toString() {
    return "Quadrilateral{" + "id='" + id + '\'' + ", points=" + points + '}';
  }
}
