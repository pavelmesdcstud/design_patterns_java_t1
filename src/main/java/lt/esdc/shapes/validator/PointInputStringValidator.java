package lt.esdc.shapes.validator;

import java.util.regex.Pattern;

public class PointInputStringValidator implements InputStringValidator {

  private final static String POINT_REGEX = "^(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)$";

  @Override
  public boolean test(String s) {
    if (s == null) {
      return false;
    }
    return Pattern.matches(POINT_REGEX, s);
  }
}
