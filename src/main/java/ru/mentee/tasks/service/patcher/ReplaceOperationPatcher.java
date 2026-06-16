package ru.mentee.tasks.service.patcher;

import org.springframework.stereotype.Service;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.api.exception.IllegalPatchOperationException;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.domain.model.TaskPriority;
import ru.mentee.tasks.domain.model.TaskStatus;

import java.time.Instant;

@Service
public class ReplaceOperationPatcher implements TaskPatcher {
  @Override
  public void patch(TaskEntity task, JsonPatchOperation operation) throws Exception {
    String path = operation.getPath();
    String value = operation.getValue();

    switch (path) {
      case "/title":
        task.setTitle(value);
        break;
      case "/description":
        task.setDescription(value);
        break;
      case "/status":
        task.setStatus(TaskStatus.valueOf(value));
        break;
      case "/priority":
        task.setPriority(TaskPriority.valueOf(value));
        break;
      case "/assignee":
        task.setAssignee(value);
        break;
      case "/dueDate":
        task.setDueDate(Instant.parse(value));
        break;
      default:
        throw new IllegalPatchOperationException(operation.getOp(), path);
    }
  }

  @Override
  public JsonPatchOperation.OpEnum type() {
    return JsonPatchOperation.OpEnum.REPLACE;
  }
}
