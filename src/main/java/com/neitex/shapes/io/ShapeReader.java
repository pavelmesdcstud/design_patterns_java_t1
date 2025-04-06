package com.neitex.shapes.io;

import com.neitex.shapes.entity.Shape;
import java.util.List;

public interface ShapeReader<S extends Shape> {

  List<S> readShapes();
}
