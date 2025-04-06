package com.neitex.shapes.entity;

import com.neitex.shapes.exception.IllegalArgumentsException;

public record Point(double x, double y) {

  public Point {
    if (Double.isNaN(x) || Double.isInfinite(x) || Double.isNaN(y) || Double.isInfinite(y)) {
      throw new IllegalArgumentsException("Point coordinates cannot be NaN or Infinite.");
    }
  }

  @Override
  public String toString() {
    return String.format("Point(%.2f, %.2f)", x, y);
  }

  public String serialize() {
    return String.format("%.2f,%.2f", x, y);
  }
}
