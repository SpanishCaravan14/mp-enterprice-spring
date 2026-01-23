package ru.mentee.tasks.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mentee.tasks.DataHelper.getTaskEntity;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.jdbc.Sql;
import ru.mentee.tasks.BaseIntegrationTest;
import ru.mentee.tasks.domain.model.TaskEntity;

@Sql(statements = """
        INSERT INTO tasks (id, title, description, status, priority, assignee, due_date, tags, created_at, updated_at)
        VALUES 
            ('00000000-0000-0000-0000-000000000001', 'Test task', 'Test description', 'TODO', 'HIGH', 'user1', '2029-01-01 00:00:00', '{tag1, tag2}', '2026-01-01 00:00:00', '2026-01-01 00:00:00'),
            ('00000000-0000-0000-0000-000000000002', 'Test task2', 'Test description', 'TODO', 'LOW', 'user1', '2029-01-01 00:00:00', '{tag1, tag2, tag3}', '2026-01-01 00:00:00', '2026-01-02 00:00:00');
        """)
public class TaskServiceIntegrationTest extends BaseIntegrationTest {

  @Test
  void whenTaskIsPresentThenFindExist() {
      assertThat(taskService.getTaskById(TASK_ID)).isNotNull();
  }

  @Test
  void whenCreateTaskThenOk() {
    TaskEntity expected = getTaskEntity();
    var id = taskService.createTask(expected).getId();
    assertThat(taskRepository.findById(id))
            .isPresent()
                    .get()
                            .satisfies(actual -> {
                              assertEquals(actual.getTitle(), expected.getTitle());
                              assertEquals(actual.getDescription(), expected.getDescription());
                              assertEquals(actual.getStatus(), expected.getStatus());
                              assertEquals(actual.getPriority(), expected.getPriority());
                              assertEquals(actual.getAssignee(), expected.getAssignee());
                                assertEquals(actual.getDueDate().truncatedTo(ChronoUnit.MILLIS), expected.getDueDate().truncatedTo(ChronoUnit.MILLIS));
                              assertThat(Arrays.stream(actual.getTagsArray()).toArray())
                                      .containsExactly(Arrays.stream(expected.getTagsArray()).toArray());
                              assertEquals(actual.getCreatedAt(), actual.getUpdatedAt());
                                assertEquals(actual.getCreatedAt().truncatedTo(ChronoUnit.MILLIS), expected.getCreatedAt().truncatedTo(ChronoUnit.MILLIS));
                                assertEquals(actual.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS), expected.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS));
                            });
  }
}
