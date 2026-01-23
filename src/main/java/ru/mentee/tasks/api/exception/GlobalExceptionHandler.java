package ru.mentee.tasks.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    ApiError apiError = new ApiError(ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TaskCreationException.class)
  public ResponseEntity<ApiError> handleTaskCreationException(TaskCreationException ex) {
    ApiError apiError = new ApiError(ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<ApiError> handleTaskNotFoundException(TaskNotFoundException ex) {
    ApiError apiError = new ApiError(ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }
}
