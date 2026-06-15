package ru.mentee.library.domain.exception;

public class NotFoundBookException extends RuntimeException {

  public NotFoundBookException() {
    super("Книга не найдена");
  }
}
