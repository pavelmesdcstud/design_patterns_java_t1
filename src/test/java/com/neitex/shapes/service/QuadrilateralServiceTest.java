package com.neitex.shapes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.neitex.shapes.entity.Point;
import com.neitex.shapes.entity.Quadrilateral;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuadrilateralServiceTest {

  private QuadrilateralService service;

  @BeforeEach
  void setUp() {
    PointService pointService = new PointService();
    service = new QuadrilateralService(pointService);
  }

  @Test
  void testCalculatePerimeter_square() {
    Quadrilateral square = new Quadrilateral("square", Arrays.asList(
        new Point(0, 0),
        new Point(1, 0),
        new Point(1, 1),
        new Point(0, 1)
    ));
    assertEquals(4.0, service.calculatePerimeter(square), 1e-9);
  }

  @Test
  void testCalculateArea_square() {
    Quadrilateral square = new Quadrilateral("square", Arrays.asList(
        new Point(0, 0),
        new Point(2, 0),
        new Point(2, 2),
        new Point(0, 2)
    ));
    assertEquals(4.0, service.calculateArea(square), 1e-9);
  }

  @Test
  void testIsValid_valid() {
    Quadrilateral valid = new Quadrilateral("valid", Arrays.asList(
        new Point(0, 0),
        new Point(1, 0),
        new Point(1, 1),
        new Point(0, 1)
    ));
    assertTrue(service.isValid(valid));
  }

  @Test
  void testIsValid_invalid() {
    Quadrilateral invalid = new Quadrilateral("invalid", Arrays.asList(
        new Point(0, 0),
        new Point(1, 1),
        new Point(2, 2),
        new Point(0, 1)
    ));
    assertFalse(service.isValid(invalid));
  }

  @Test
  void testIsSquare_square() {
    Quadrilateral square = new Quadrilateral("square", Arrays.asList(
        new Point(0, 0),
        new Point(1, 0),
        new Point(1, 1),
        new Point(0, 1)
    ));
    assertTrue(service.isSquare(square));
  }

  @Test
  void testIsSquare_rectangle() {
    Quadrilateral rectangle = new Quadrilateral("rectangle", Arrays.asList(
        new Point(0, 0),
        new Point(2, 0),
        new Point(2, 1),
        new Point(0, 1)
    ));
    assertFalse(service.isSquare(rectangle));
  }

  @Test
  void testIsRectangle_rectangle() {
    Quadrilateral rectangle = new Quadrilateral("rectangle", Arrays.asList(
        new Point(0, 0),
        new Point(2, 0),
        new Point(2, 1),
        new Point(0, 1)
    ));
    assertTrue(service.isRectangle(rectangle));
  }

  @Test
  void testIsRectangle_rhombus() {
    Quadrilateral rhombus = new Quadrilateral("rhombus", Arrays.asList(
        new Point(0, 0),
        new Point(0.5, 1),
        new Point(0, 2),
        new Point(-0.5, 1)
    ));
    assertFalse(service.isRectangle(rhombus));
  }

  @Test
  void testIsRhombus_rhombus() {
    Quadrilateral rhombus = new Quadrilateral("rhombus", Arrays.asList(
        new Point(0, 0),
        new Point(0.5, 1),
        new Point(0, 2),
        new Point(-0.5, 1)
    ));
    assertTrue(service.isRhombus(rhombus));
  }

  @Test
  void testIsRhombus_square() {
    Quadrilateral square = new Quadrilateral("square", Arrays.asList(
        new Point(0, 0),
        new Point(0, 1),
        new Point(1, 1),
        new Point(1, 0)
    ));
    assertTrue(service.isRhombus(square));
  }

  @Test
  void testIsRhombus_rectangle() {
    Quadrilateral rectangle = new Quadrilateral("rectangle", Arrays.asList(
        new Point(0, 0),
        new Point(2, 0),
        new Point(2, 1),
        new Point(0, 1)
    ));
    assertFalse(service.isRhombus(rectangle));
  }

  @Test
  void testIsTrapezoid_trapezoid() {
    Quadrilateral trapezoid = new Quadrilateral("trapezoid", Arrays.asList(
        new Point(0, 0),
        new Point(3, 0),
        new Point(2, 1),
        new Point(1, 1)
    ));
    assertTrue(service.isTrapezoid(trapezoid));
  }

  @Test
  void testIsTrapezoid_irregular() {
    Quadrilateral irregular = new Quadrilateral("irregular", Arrays.asList(
        new Point(0, 0),
        new Point(2, 1),
        new Point(1, 2),
        new Point(0, 1)
    ));
    assertFalse(service.isTrapezoid(irregular));
  }

  @Test
  void testIsConvex_convex() {
    Quadrilateral convex = new Quadrilateral("convex", Arrays.asList(
        new Point(0, 0),
        new Point(2, 0),
        new Point(2, 2),
        new Point(0, 2)
    ));
    assertTrue(service.isConvex(convex));
  }

  @Test
  void testIsConvex_concave() {
    Quadrilateral concave = new Quadrilateral("concave", Arrays.asList(
        new Point(0, 0),
        new Point(2, 0),
        new Point(1, 0.5),
        new Point(0, 2)
    ));
    assertFalse(service.isConvex(concave));
  }
}
