package ru.mentee.library.service.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IsbnValidator {

  public static boolean validateIsbn(String isbn) {
    if (isbn == null || isbn.trim().isEmpty()) {
      return false;
    }
    return isbn.matches("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$");
  }
}
