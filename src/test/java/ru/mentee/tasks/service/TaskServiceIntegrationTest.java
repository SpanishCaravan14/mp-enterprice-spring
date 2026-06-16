package ru.mentee.tasks.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.BaseIntegrationTest;
import ru.mentee.tasks.api.exception.IllegalPatchOperationException;
import ru.mentee.tasks.api.exception.TaskNotFoundException;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.domain.model.TaskPriority;
import ru.mentee.tasks.domain.model.TaskStatus;
import ru.mentee.tasks.domain.search.SearchInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.mentee.tasks.DataHelper.getTaskEntity;

@Sql(
    statements =
        """
        INSERT INTO tasks (id, title, description, status, priority, assignee, due_date, tags, created_at, updated_at)
        VALUES
            ('00000000-0000-0000-0000-000000000001', 'Test task', 'Test description', 'TODO', 'HIGH', 'user1', '2029-01-01 00:00:00', '{tag1, tag2}', '2026-01-01 00:00:00', '2026-01-01 00:00:00'),
            ('00000000-0000-0000-0000-000000000002', 'Test task2', 'Test description', 'IN_PROGRESS', 'LOW', 'user1', '2029-01-01 00:00:00', '{tag1, tag2, tag3}', '2026-01-01 00:00:01', '2026-01-02 00:00:00'),
            ('00000000-0000-0000-0000-000000000003', 'Test task3', 'Test description', 'TODO', 'HIGH', 'user1', '2029-01-01 00:00:00', '{tag1, tag2}', '2026-01-01 00:00:02', '2026-01-01 00:00:00');
        """)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TaskServiceIntegrationTest extends BaseIntegrationTest {

  @Test
  void whenTaskIsPresentThenFindExist() {
    assertThat(taskService.searchTaskById(TASK_ID)).isNotNull();
  }

  @Test
  void whenTaskIsNotPresentThenException() {
    assertThatThrownBy(() -> taskService.searchTaskById(UUID.randomUUID()))
        .isInstanceOf(TaskNotFoundException.class)
        .hasMessage("Задача не найдена");
  }

  @Test
  void whenCreateTaskThenOk() {
    TaskEntity expected = getTaskEntity();
    var id = taskService.createTask(expected).getId();
    taskRepository.flush();
    entityManager.clear();

    assertThat(taskRepository.findById(id))
        .isPresent()
        .get()
        .satisfies(
            actual -> {
              assertEquals(expected.getTitle(), actual.getTitle());
              assertEquals(expected.getDescription(), actual.getDescription());
              assertEquals(expected.getPriority(), actual.getPriority());
              assertEquals(expected.getAssignee(), actual.getAssignee());
              assertEquals(TaskStatus.TODO, actual.getStatus());
              assertEquals(
                  expected.getDueDate().truncatedTo(ChronoUnit.MILLIS),
                  actual.getDueDate().truncatedTo(ChronoUnit.MILLIS));
              assertThat(Arrays.stream(actual.getTagsArray()).toArray())
                  .containsExactly(Arrays.stream(expected.getTagsArray()).toArray());
              assertEquals(actual.getUpdatedAt(), actual.getCreatedAt());
              assertEquals(
                  expected.getCreatedAt().truncatedTo(ChronoUnit.MILLIS),
                  actual.getCreatedAt().truncatedTo(ChronoUnit.MILLIS));
              assertEquals(
                  expected.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS),
                  actual.getUpdatedAt().truncatedTo(ChronoUnit.MILLIS));
            });
  }

  @Test
  void whenUpdateTaskThenOk() {
    TaskEntity expected = getTaskEntity();
    expected.setStatus(TaskStatus.IN_PROGRESS);

    taskService.updateTask(TASK_ID, expected);
    taskRepository.flush();
    entityManager.clear();

    assertThat(taskRepository.findById(TASK_ID))
        .isPresent()
        .get()
        .satisfies(
            actual -> {
              assertEquals(expected.getTitle(), actual.getTitle());
              assertEquals(expected.getDescription(), actual.getDescription());
              assertEquals(expected.getStatus(), actual.getStatus());
              assertEquals(expected.getPriority(), actual.getPriority());
              assertEquals(expected.getAssignee(), actual.getAssignee());
              assertEquals(
                  expected.getDueDate().truncatedTo(ChronoUnit.MILLIS),
                  actual.getDueDate().truncatedTo(ChronoUnit.MILLIS));
              assertThat(Arrays.stream(actual.getTagsArray()).toArray())
                  .containsExactly(Arrays.stream(expected.getTagsArray()).toArray());
              assertNotEquals(actual.getCreatedAt(), actual.getUpdatedAt());
            });
  }

  @Test
  void whenApplyCorrectPatchOperationsToTaskThenOk() {
    var expected = taskRepository.findById(TASK_ID).get();

    List<JsonPatchOperation> operations =
        List.of(
            new JsonPatchOperation()
                .op(JsonPatchOperation.OpEnum.ADD)
                .path("/tags")
                .value("newTag"),
            new JsonPatchOperation()
                .op(JsonPatchOperation.OpEnum.REMOVE)
                .path("/tags")
                .value("tag2"),
            new JsonPatchOperation()
                .op(JsonPatchOperation.OpEnum.REPLACE)
                .path("/priority")
                .value("LOW"),
            new JsonPatchOperation()
                .op(JsonPatchOperation.OpEnum.REPLACE)
                .path("/description")
                .value("new description"));
    taskService.patchTask(TASK_ID, operations);
    taskRepository.flush();
    entityManager.clear();

    assertThat(taskRepository.findById(TASK_ID))
        .isPresent()
        .get()
        .satisfies(
            actual -> {
              assertEquals(expected.getTitle(), actual.getTitle());
              assertEquals("new description", actual.getDescription());
              assertEquals(expected.getStatus(), actual.getStatus());
              assertEquals(TaskPriority.LOW, actual.getPriority());
              assertEquals(expected.getAssignee(), actual.getAssignee());
              assertEquals(
                  expected.getDueDate().truncatedTo(ChronoUnit.MILLIS),
                  actual.getDueDate().truncatedTo(ChronoUnit.MILLIS));
              assertThat(Arrays.stream(actual.getTagsArray()).toArray())
                  .containsExactly("tag1", "newTag");
              assertNotEquals(actual.getCreatedAt(), actual.getUpdatedAt());
            });
  }

  @Test
  void whenApplyPatchOperationToWrongFieldThanException() {
    assertThatThrownBy(
            () ->
                taskService.patchTask(
                    TASK_ID,
                    List.of(
                        new JsonPatchOperation()
                            .op(JsonPatchOperation.OpEnum.REPLACE)
                            .path("/createdAt")
                            .value(Instant.now().toString()))))
        .isInstanceOf(IllegalPatchOperationException.class);

    assertThatThrownBy(
            () ->
                taskService.patchTask(
                    TASK_ID,
                    List.of(
                        new JsonPatchOperation()
                            .op(JsonPatchOperation.OpEnum.ADD)
                            .path("/createdAt")
                            .value(Instant.now().toString()))))
        .isInstanceOf(IllegalPatchOperationException.class);

    assertThatThrownBy(
            () ->
                taskService.patchTask(
                    TASK_ID,
                    List.of(
                        new JsonPatchOperation()
                            .op(JsonPatchOperation.OpEnum.REMOVE)
                            .path("/title")
                            .value("taggy"))))
        .isInstanceOf(IllegalPatchOperationException.class);
  }

  @Test
  void whenSearchOneTaskThenOk() {
    SearchInfo.Filter filter = new SearchInfo.Filter("TODO", null, null);
    Sort sort = Sort.by(Sort.Order.desc("createdAt"));
    SearchInfo searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(2);
              assertThat(rs.getTasks()).isNotEmpty();
            });
  }

  @Test
  void whenSearchFewTasksThenOk() {
    SearchInfo.Filter filter = new SearchInfo.Filter(null, null, null);
    Sort sort = Sort.by(Sort.Order.desc("createdAt"));
    SearchInfo searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(3);
              assertThat(rs.getTasks()).isNotEmpty();
            });

    filter = new SearchInfo.Filter("TODO", "user1", null);
    sort = Sort.by(Sort.Order.desc("createdAt"));
    searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(2);
              assertThat(rs.getTasks()).isNotEmpty();
            });
  }

  @Test
  void whenSearchByNotPresentStatusThenEmpty() {
    SearchInfo.Filter filter = new SearchInfo.Filter("NO_SUCH_STATUS", null, null);
    Sort sort = Sort.by(Sort.Order.desc("createdAt"));
    SearchInfo searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(0);
              assertThat(rs.getTasks()).isEmpty();
            });
  }

  @Test
  void whenSearchByPresentStatusAndNotPresentAssigneeThenEmpty() {
    SearchInfo.Filter filter = new SearchInfo.Filter("TODO", "serg", null);
    Sort sort = Sort.by(Sort.Order.desc("createdAt"));
    SearchInfo searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(0);
              assertThat(rs.getTasks()).isEmpty();
            });
  }

  @Test
  void whenSearchWithOrderDescThenCorrectThird() {
    SearchInfo.Filter filter = new SearchInfo.Filter("TODO", "user1", null);
    Sort sort = Sort.by(Sort.Order.desc("createdAt"));
    SearchInfo searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(2);
              assertThat(rs.getTasks().getFirst().getId())
                  .isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000003"));
            });
  }

  @Test
  void whenSearchWithOrderAscThenCorrectFirst() {
    SearchInfo.Filter filter = new SearchInfo.Filter("TODO", "user1", null);
    Sort sort = Sort.by(Sort.Order.asc("createdAt"));
    SearchInfo searchInfo = new SearchInfo(filter, PageRequest.of(0, 20, sort));

    assertThat(taskService.searchTasks(searchInfo))
        .satisfies(
            rs -> {
              assertThat(rs.getTotalElements()).isEqualTo(2);
              assertThat(rs.getTasks().getFirst().getId()).isEqualTo(TASK_ID);
            });
  }

  @Test
  void whenDeleteExistingTaskThenOk() {
    assertDoesNotThrow(() -> taskService.deleteTask(TASK_ID));
  }
}
