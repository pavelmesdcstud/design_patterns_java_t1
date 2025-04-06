package com.neitex.shapes.io;

import com.neitex.shapes.exception.MalformedInputStringException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointStringReaderTest {

  @ParameterizedTest
  @ValueSource(strings = {"0.1,0.2", "0.0,0.0", "1.0,-1049123123.1",
      "-0.0,-0.0", "1,2"})
  void testValidPoints(String point) {
    PointStringReader pointStringReader = new PointStringReader();
    Assertions.assertDoesNotThrow(() -> {
      pointStringReader.read(point);
    }, "Expected no exception for valid point: " + point);
  }

  @ParameterizedTest
  @ValueSource(strings = {"0.1,0.2,0.3", "0.0,0.0,0.1", "a,b",
      "1.0.0,1.0", "-0.0;-0.0", "1a, 2", "1, 2", "1.2 1", "12w,01", "(0.1,0.2)"})
  void testInvalidPoints(String point) {
    PointStringReader pointStringReader = new PointStringReader();
    Assertions.assertThrows(MalformedInputStringException.class,
        () -> pointStringReader.read(point),
        "Expected MalformedInputStringException for invalid point: " + point);
  }
}
