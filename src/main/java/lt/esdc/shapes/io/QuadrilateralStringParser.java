package lt.esdc.shapes.io;

import java.util.ArrayList;
import java.util.List;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Quadrilateral;
import lt.esdc.shapes.exception.MalformedInputStringException;
import lt.esdc.shapes.factory.QuadrilateralFactory;
import lt.esdc.shapes.factory.ShapeFactory;
import lt.esdc.shapes.validator.InputStringValidator;
import lt.esdc.shapes.validator.QuadrilateralInputStringValidator;

public class QuadrilateralStringParser implements StringParser<Quadrilateral> {

  private final InputStringValidator inputStringValidator = new QuadrilateralInputStringValidator();
  private final PointStringParser pointReader = new PointStringParser();
  private final ShapeFactory<Quadrilateral> quadrilateralShapeFactory = new QuadrilateralFactory();

  @Override
  public Quadrilateral parse(String shapeData) throws MalformedInputStringException {
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
        points.add(pointReader.parse(str));
      } catch (MalformedInputStringException e) {
        throw new MalformedInputStringException("Invalid point data: " + str, e);
      } catch (RuntimeException e) {
        throw new MalformedInputStringException("Invalid point input string: " + str, e);
      }
    }
    if (points.size() != 4) {
      throw new MalformedInputStringException("Invalid quadrilateral data: " + shapeData);
    }
    return quadrilateralShapeFactory.create(points);
  }
}
