package lt.esdc.shapes.io;

import lt.esdc.shapes.exception.MalformedInputStringException;

public interface StringReader<S> {

  S read(String shapeData) throws MalformedInputStringException;

}
