package lt.esdc.shapes.validator;

public class QuadrilateralInputStringValidator implements InputStringValidator {
  private final PointInputStringValidator pointInputStringValidator = new PointInputStringValidator();

  @Override
  public boolean test(String s) {
    String[] points = s.split(";");
    if (points.length != 4) {
      return false;
    }
    for (String point : points) {
      if (!pointInputStringValidator.test(point)) {
        return false;
      }
    }
    return true;
  }
}
