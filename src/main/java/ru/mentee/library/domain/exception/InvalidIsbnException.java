package ru.mentee.library.domain.exception;

public class InvalidIsbnException extends RuntimeException {

  public InvalidIsbnException() {
    super("Некорректный ISBN");
  }
}
