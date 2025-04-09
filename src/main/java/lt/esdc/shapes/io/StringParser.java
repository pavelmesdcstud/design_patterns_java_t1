package lt.esdc.shapes.io;

import lt.esdc.shapes.exception.MalformedInputStringException;

public interface StringParser<S> {

  S parse(String shapeData) throws MalformedInputStringException;

}
