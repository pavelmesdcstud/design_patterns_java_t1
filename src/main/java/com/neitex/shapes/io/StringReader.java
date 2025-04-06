package com.neitex.shapes.io;

import com.neitex.shapes.exception.MalformedInputStringException;

public interface StringReader<S> {

  S read(String shapeData) throws MalformedInputStringException;

}
