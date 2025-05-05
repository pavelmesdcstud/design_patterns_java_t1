package lt.esdc.shapes.parser;

import lt.esdc.shapes.exception.MalformedInputStringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointStringParserTest {

  @ParameterizedTest
  @ValueSource(strings = {"0.1,0.2", "0.0,0.0", "1.0,-1049123123.1",
      "-0.0,-0.0", "1,2"})
  void testValidPoints(String point) {
    PointStringParser pointStringReader = new PointStringParser();
    Assertions.assertDoesNotThrow(() -> {
      pointStringReader.parse(point);
    }, "Expected no exception for valid point: " + point);
  }

  @ParameterizedTest
  @ValueSource(strings = {"0.1,0.2,0.3", "0.0,0.0,0.1", "a,b",
      "1.0.0,1.0", "-0.0;-0.0", "1a, 2", "1, 2", "1.2 1", "12w,01", "(0.1,0.2)"})
  void testInvalidPoints(String point) {
    PointStringParser pointStringReader = new PointStringParser();
    Assertions.assertThrows(MalformedInputStringException.class,
        () -> pointStringReader.parse(point),
        "Expected MalformedInputStringException for invalid point: " + point);
  }
}
