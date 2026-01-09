package ru.mentee.library.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    log.error("Ошибка валидации входных данных");
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidIsbnException.class)
  public ResponseEntity<ApiError> handleInvalidIsbnException(InvalidIsbnException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundBookException.class)
  public ResponseEntity<ApiError> handleBookNotFoundException(NotFoundBookException ex) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }
}
