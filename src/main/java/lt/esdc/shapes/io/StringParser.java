package lt.esdc.shapes.io;

import lt.esdc.shapes.exception.MalformedInputStringException;

public interface StringParser<T> {

  T parse(String shapeData) throws MalformedInputStringException;

}
