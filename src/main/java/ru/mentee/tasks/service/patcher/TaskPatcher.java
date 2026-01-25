package ru.mentee.tasks.service.patcher;

import ru.mentee.tasks.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.domain.model.TaskEntity;

public interface TaskPatcher {
  void patch(TaskEntity task, JsonPatchOperation operation) throws Exception;

  JsonPatchOperation.OpEnum type();
}
