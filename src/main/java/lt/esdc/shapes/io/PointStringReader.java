package lt.esdc.shapes.io;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.exception.MalformedInputStringException;
import lt.esdc.shapes.validator.PointInputStringValidator;

public class PointStringReader implements StringReader<Point> {
  private final PointInputStringValidator inputStringValidator = new PointInputStringValidator();

  @Override
  public Point read(String shapeData) throws MalformedInputStringException {
    if (!inputStringValidator.test(shapeData)) {
      throw new MalformedInputStringException("Invalid input string: " + shapeData);
    }
    String[] coordinates = shapeData.split(",");
    if (coordinates.length != 2) {
      throw new MalformedInputStringException("Invalid point data: " + shapeData);
    }
    try {
      double x = Double.parseDouble(coordinates[0]);
      double y = Double.parseDouble(coordinates[1]);
      return new Point(x, y);
    } catch (NumberFormatException e) {
      throw new MalformedInputStringException("Invalid point data: " + shapeData);
    }
  }
}
