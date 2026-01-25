package ru.mentee.tasks.api.exception;

import static java.lang.String.format;

import ru.mentee.tasks.api.generated.dto.JsonPatchOperation;

public class IllegalPatchOperationException extends RuntimeException {
  public IllegalPatchOperationException(JsonPatchOperation.OpEnum operationType, String parameter) {
    super(
        format(
            "Операция %s с параметром задачи %s не поддерживается",
            operationType.getValue(), parameter));
  }
}
