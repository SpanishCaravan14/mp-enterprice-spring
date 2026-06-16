package ru.mentee.tasks.service.patcher;

import org.springframework.stereotype.Service;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.api.exception.IllegalPatchOperationException;
import ru.mentee.tasks.domain.model.TaskEntity;

import java.util.Arrays;

@Service
public class RemoveOperationPatcher implements TaskPatcher {
  @Override
  public void patch(TaskEntity task, JsonPatchOperation operation) throws Exception {
    String path = operation.getPath();
    String value = operation.getValue();

    switch (path) {
      case "/tags":
        removeTag(task, value);
        break;
      default:
        throw new IllegalPatchOperationException(operation.getOp(), path);
    }
  }

  @Override
  public JsonPatchOperation.OpEnum type() {
    return JsonPatchOperation.OpEnum.REMOVE;
  }

  private void removeTag(TaskEntity current, String tag) {
    if (current.getTagsArray() != null) {
      current.setTagsArray(
          Arrays.stream(current.getTagsArray()).filter(t -> !t.equals(tag)).toArray(String[]::new));
    }
  }
}
