package ru.mentee.tasks.api.exception;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException() {
    super("Задача не найдена");
  }
}
