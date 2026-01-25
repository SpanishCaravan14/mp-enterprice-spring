package ru.mentee.tasks.service.patcher;

import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import ru.mentee.tasks.api.exception.IllegalPatchOperationException;
import ru.mentee.tasks.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.domain.model.TaskEntity;

@Service
public class AddOperationPatcher implements TaskPatcher {
  @Override
  public void patch(TaskEntity task, JsonPatchOperation operation) throws Exception {
    String path = operation.getPath();
    String value = operation.getValue();

    switch (path) {
      case "/tags":
        addTag(task, value);
        break;
      default:
        throw new IllegalPatchOperationException(operation.getOp(), path);
    }
  }

  @Override
  public JsonPatchOperation.OpEnum type() {
    return JsonPatchOperation.OpEnum.ADD;
  }

  private void addTag(TaskEntity current, String tag) {
    if (current.getTagsArray() == null) {
      current.setTagsArray(new String[] {tag});
    } else {
      current.setTagsArray(
          Stream.concat(Arrays.stream(current.getTagsArray()), Stream.of(tag))
              .toArray(String[]::new));
    }
  }
}
