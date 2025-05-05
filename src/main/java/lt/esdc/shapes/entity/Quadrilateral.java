// ./lt/esdc/shapes/entity/Quadrilateral.java
package lt.esdc.shapes.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lt.esdc.shapes.observer.ObservableShape;
import lt.esdc.shapes.observer.ShapeObserver;

// Changed from record to class, implements Shape and ObservableShape
public class Quadrilateral implements Shape, ObservableShape {

  private final String id;
  private final List<ShapeObserver> observers; // Observer list
  private List<Point> points; // Make mutable

  // Constructor
  public Quadrilateral(String id, List<Point> points) {
    this.id = Objects.requireNonNull(id, "ID cannot be null");
    setPointsInternal(points);
    this.observers = new ArrayList<>();
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public List<Point> points() {
    return Collections.unmodifiableList(points);
  }

  public void setPoints(List<Point> newPoints) {
    setPointsInternal(newPoints);
    notifyObservers();
  }

  private void setPointsInternal(List<Point> newPoints) {
    if (newPoints == null || newPoints.size() != 4) {
      throw new IllegalArgumentException("Quadrilateral must have exactly 4 points.");
    }
    this.points = List.copyOf(newPoints);
  }

  public Point getP1() {
    return points.get(0);
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
  public void addObserver(ShapeObserver observer) {
    if (observer != null && !observers.contains(observer)) {
      observers.add(observer);
    }
  }

  @Override
  public void removeObserver(ShapeObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (ShapeObserver observer : new ArrayList<>(observers)) {
      observer.update(this);
    }
  }

  @Override
  public String toString() {
    return "Quadrilateral{" + "id='" + id + '\'' + ", points=" + points + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Quadrilateral that = (Quadrilateral) o;
    return id.equals(that.id) && points.equals(that.points) && observers.equals(that.observers);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + points.hashCode();
    result = 31 * result + observers.hashCode();
    return result;
  }
}
