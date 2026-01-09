package ru.mentee.library.domain.exception;

public class NotFoundBookException extends RuntimeException {

  public NotFoundBookException() {
    this("Книга не найдена");
  }

  private NotFoundBookException(String message) {
    super(message);
  }
}
