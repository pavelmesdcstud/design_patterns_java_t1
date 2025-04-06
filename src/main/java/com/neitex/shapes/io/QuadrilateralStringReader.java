package com.neitex.shapes.io;

import com.neitex.shapes.entity.Point;
import com.neitex.shapes.entity.Quadrilateral;
import com.neitex.shapes.exception.MalformedInputStringException;
import com.neitex.shapes.factory.QuadrilateralFactory;
import com.neitex.shapes.factory.ShapeFactory;
import com.neitex.shapes.validator.InputStringValidator;
import com.neitex.shapes.validator.QuadrilateralDataValidator;
import com.neitex.shapes.validator.QuadrilateralInputStringValidator;
import com.neitex.shapes.validator.ShapeDataValidator;
import java.util.ArrayList;
import java.util.List;

public class QuadrilateralStringReader implements StringReader<Quadrilateral> {

  private final InputStringValidator inputStringValidator = new QuadrilateralInputStringValidator();
  private final ShapeDataValidator quadrilateralDataValidator = new QuadrilateralDataValidator();
  private final PointStringReader pointReader = new PointStringReader();
  private final ShapeFactory<Quadrilateral> quadrilateralShapeFactory = new QuadrilateralFactory();

  @Override
  public Quadrilateral read(String shapeData) throws MalformedInputStringException {
    if (shapeData == null || !inputStringValidator.test(shapeData)) {
      throw new MalformedInputStringException("Invalid input string: " + shapeData);
    }
    List<Point> points = new ArrayList<>(4);
    String[] pts = shapeData.split(";");
    for (String pt : pts) {
      if (pt == null || pt.isEmpty()) {
        throw new MalformedInputStringException("Invalid point data: " + pt);
      }
      String str = pt.trim();
      try {
        points.add(pointReader.read(str));
      } catch (MalformedInputStringException e) {
        throw new MalformedInputStringException("Invalid point data: " + str, e);
      } catch (RuntimeException e) {
        throw new MalformedInputStringException("Invalid point input string: " + str, e);
      }
    }
    if (points.size() != 4) {
      throw new MalformedInputStringException("Invalid quadrilateral data: " + shapeData);
    }
    if (!quadrilateralDataValidator.test(points)) {
      throw new MalformedInputStringException("Invalid quadrilateral data: " + points);
    }
    return quadrilateralShapeFactory.create(points);
  }
}
