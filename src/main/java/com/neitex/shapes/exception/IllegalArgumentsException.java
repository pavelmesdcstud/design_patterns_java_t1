package com.neitex.shapes.exception;

public class IllegalArgumentsException extends IllegalArgumentException {

  public IllegalArgumentsException() {
    super();
  }

  public IllegalArgumentsException(String s) {
    super(s);
  }

  public IllegalArgumentsException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalArgumentsException(Throwable cause) {
    super(cause);
  }
}
