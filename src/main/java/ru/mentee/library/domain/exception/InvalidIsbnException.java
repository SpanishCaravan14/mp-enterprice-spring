package ru.mentee.library.domain.exception;

public class InvalidIsbnException extends RuntimeException {

  public InvalidIsbnException() {
    this("Некорректный ISBN");
  }

  private InvalidIsbnException(String message) {
    super(message);
  }
}
