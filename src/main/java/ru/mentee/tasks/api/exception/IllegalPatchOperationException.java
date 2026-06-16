package ru.mentee.tasks.api.exception;

import ru.mentee.api.generated.dto.JsonPatchOperation;

import static java.lang.String.format;


public class IllegalPatchOperationException extends RuntimeException {
  public IllegalPatchOperationException(JsonPatchOperation.OpEnum operationType, String parameter) {
    super(
        format(
            "Операция %s с параметром задачи %s не поддерживается",
            operationType.getValue(), parameter));
  }
}
