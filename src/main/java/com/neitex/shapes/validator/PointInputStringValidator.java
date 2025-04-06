package com.neitex.shapes.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PointInputStringValidator implements InputStringValidator {

  private final static String POINT_REGEX = "^(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)$";

  @Override
  public boolean test(String s) {
    if (s == null) {
      return false;
    }
    Matcher matcher = Pattern.compile(POINT_REGEX).matcher(s);
    return matcher.find();
  }
}
