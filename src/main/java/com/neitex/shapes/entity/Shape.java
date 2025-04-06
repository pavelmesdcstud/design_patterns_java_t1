package com.neitex.shapes.entity;

import java.util.List;

public interface Shape {

  String id();

  List<Point> points();

  String serialize();
}
