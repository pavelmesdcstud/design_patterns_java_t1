package com.neitex.shapes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.neitex.shapes.entity.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointServiceTest {

  private PointService service;

  @BeforeEach
  void setUp() {
    service = new PointService();
  }

  @Test
  void testSquaredDistance_1() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(3, 4);
    assertEquals(25.0, service.squaredDistance(p1, p2), 1e-9);
  }

  @Test
  void testSquaredDistance_2() {
    Point p3 = new Point(-1, -2);
    Point p4 = new Point(2, 1);
    assertEquals(18.0, service.squaredDistance(p3, p4), 1e-9);
  }

  @Test
  void testSquaredDistance_3() {
    Point p1 = new Point(0, 0);
    assertEquals(0.0, service.squaredDistance(p1, p1), 1e-9);
  }

  @Test
  void testDistance_1() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(3, 4);
    assertEquals(5.0, service.distance(p1, p2), 1e-9);
  }

  @Test
  void testDistance_2() {
    Point p3 = new Point(-1, -1);
    Point p4 = new Point(2, 2);
    assertEquals(Math.sqrt(18), service.distance(p3, p4), 1e-9);
  }

  @Test
  void testDistance_3() {
    Point p1 = new Point(0, 0);
    assertEquals(0.0, service.distance(p1, p1), 1e-9);
  }

  @Test
  void testAreCollinear_1() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(1, 1);
    Point p3 = new Point(2, 2);
    assertTrue(service.areCollinear(p1, p2, p3));
  }

  @Test
  void testAreCollinear_2() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(1, 1);
    Point p4 = new Point(0, 2);
    assertFalse(service.areCollinear(p1, p2, p4));
  }

  @Test
  void testAreCollinear_3() {
    Point p5 = new Point(0, 0);
    Point p6 = new Point(2, 0);
    Point p7 = new Point(5, 0);
    assertTrue(service.areCollinear(p5, p6, p7));
  }

  @Test
  void testAreCollinear_4() {
    Point p8 = new Point(0, 0);
    Point p9 = new Point(0, 2);
    Point p10 = new Point(0, 5);
    assertTrue(service.areCollinear(p8, p9, p10));
  }

  @Test
  void testAreCollinear_5() {
    Point p11 = new Point(0, 0);
    Point p12 = new Point(1, 1);
    Point p13 = new Point(2, 2.0000000005);
    assertTrue(service.areCollinear(p11, p12, p13));
  }

  @Test
  void testAreCollinear_6() {
    Point p14 = new Point(0, 0);
    Point p15 = new Point(1, 1);
    Point p16 = new Point(2, 0);
    assertFalse(service.areCollinear(p14, p15, p16));
  }

  @Test
  void testCrossProductZ_1() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(1, 0);
    Point p3 = new Point(1, 1);
    assertTrue(service.crossProductZ(p1, p2, p3) > 0);
  }

  @Test
  void testCrossProductZ_2() {
    Point p4 = new Point(0, 0);
    Point p5 = new Point(1, 0);
    Point p6 = new Point(0, -1);
    assertTrue(service.crossProductZ(p4, p5, p6) < 0);
  }

  @Test
  void testCrossProductZ_3() {
    Point p7 = new Point(0, 0);
    Point p8 = new Point(1, 1);
    Point p9 = new Point(2, 2);
    assertEquals(0.0, service.crossProductZ(p7, p8, p9), 1e-9);
  }

  @Test
  void testCrossProductZ_4() {
    Point p10 = new Point(0, 0);
    Point p11 = new Point(2, 0);
    Point p12 = new Point(0, 2);
    assertEquals(4.0, service.crossProductZ(p10, p11, p12), 1e-9);
  }
}
