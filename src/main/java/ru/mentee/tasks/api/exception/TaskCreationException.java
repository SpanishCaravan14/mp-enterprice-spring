package ru.mentee.tasks.api.exception;

public class TaskCreationException extends RuntimeException {
  public TaskCreationException() {
    super("Ошибка создания заявки");
  }
}
