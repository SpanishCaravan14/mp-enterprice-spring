package ru.mentee.tasks.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mentee.tasks.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.service.patcher.TaskPatcher;

@Service
@RequiredArgsConstructor
public class TaskPatchService {
  private final List<TaskPatcher> patchers;

  public void patch(TaskEntity task, List<JsonPatchOperation> operations) throws Exception {
    for (JsonPatchOperation operation : operations) {
      patch(task, operation);
    }
  }

  public void patch(TaskEntity task, JsonPatchOperation operation) throws Exception {
    for (var patcher : patchers) {
      if (operation.getOp().equals(patcher.type())) {
        patcher.patch(task, operation);
      }
    }
  }
}
