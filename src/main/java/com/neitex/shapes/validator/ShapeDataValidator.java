package com.neitex.shapes.validator;

import com.neitex.shapes.entity.Point;
import java.util.List;
import java.util.function.Predicate;

public interface ShapeDataValidator extends Predicate<List<Point>> {

}
