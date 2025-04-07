package lt.esdc.shapes.exception;

public class MalformedInputStringException extends Exception {

  public MalformedInputStringException(String message) {
    super(message);
  }

  public MalformedInputStringException(String message, Throwable cause) {
    super(message, cause);
  }
}
