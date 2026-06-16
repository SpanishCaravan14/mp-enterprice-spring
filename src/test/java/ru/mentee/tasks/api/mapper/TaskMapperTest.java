package ru.mentee.tasks.api.mapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.mentee.api.generated.dto.CreateTaskRequest;
import ru.mentee.tasks.domain.model.TaskPriority;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskMapperTest {
  TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

  @Test
  void toTaskEntityTest() {
    CreateTaskRequest createTaskRequest = Instancio.of(CreateTaskRequest.class).create();

    assertThat(taskMapper.toEntity(createTaskRequest))
        .satisfies(
            taskEntity -> {
              assertEquals(createTaskRequest.getTitle(), taskEntity.getTitle());
              assertEquals(createTaskRequest.getDescription(), taskEntity.getDescription());
              assertNull(taskEntity.getStatus());
              assertNull(taskEntity.getCreatedAt());
              assertNull(taskEntity.getUpdatedAt());
              assertNull(taskEntity.getId());
              assertEquals(
                  TaskPriority.valueOf(createTaskRequest.getPriority().getValue()),
                  taskEntity.getPriority());
              assertEquals(createTaskRequest.getAssignee(), taskEntity.getAssignee());
              assertEquals(
                  createTaskRequest.getDueDate().toInstant().truncatedTo(ChronoUnit.MILLIS),
                  taskEntity.getDueDate().truncatedTo(ChronoUnit.MILLIS));
              assertThat(taskEntity.getTagsArray())
                  .containsExactly(createTaskRequest.getTags().toArray(new String[0]));
            });
  }
}
