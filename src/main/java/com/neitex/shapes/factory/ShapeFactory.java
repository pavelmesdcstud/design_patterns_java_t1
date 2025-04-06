package com.neitex.shapes.factory;

import com.neitex.shapes.entity.Point;
import com.neitex.shapes.entity.Shape;
import java.util.List;

public interface  ShapeFactory<S extends Shape> {

  S create(List<Point> points);
}
