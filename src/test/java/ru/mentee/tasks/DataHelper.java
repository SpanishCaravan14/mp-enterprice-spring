package ru.mentee.tasks;

import static org.instancio.Select.field;

import org.instancio.Instancio;
import ru.mentee.tasks.domain.model.TaskEntity;

public final class DataHelper {
  private DataHelper() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static TaskEntity getTaskEntity() {
    return Instancio.of(TaskEntity.class)
        .set(field(TaskEntity::getId), null)
        .set(field(TaskEntity::getCreatedAt), null)
        .set(field(TaskEntity::getUpdatedAt), null)
        .set(field(TaskEntity::getStatus), null)
        .create();
  }
}
