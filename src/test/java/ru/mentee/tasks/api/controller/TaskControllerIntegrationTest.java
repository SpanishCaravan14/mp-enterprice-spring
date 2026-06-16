package ru.mentee.tasks.api.controller;

import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import ru.mentee.api.generated.dto.CreateTaskRequest;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.api.generated.dto.Task;
import ru.mentee.api.generated.dto.UpdateTaskRequest;
import ru.mentee.tasks.BaseIntegrationTest;
import ru.mentee.tasks.domain.model.TaskEntity;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(
    statements =
        """
                INSERT INTO tasks (id, title, description, status, priority, assignee, due_date, tags, created_at, updated_at)
                VALUES
                    ('00000000-0000-0000-0000-000000000001', 'Test task', 'Test description', 'DONE', 'HIGH', 'user1', '2029-01-01 00:00:00', '{tag1, tag2}', '2026-01-01 00:00:00', '2026-01-01 00:00:00'),
                    ('00000000-0000-0000-0000-000000000002', 'Test task2', 'Test description', 'IN_PROGRESS', 'LOW', 'user1', '2029-01-01 00:00:00', '{tag1, tag2, tag3}', '2026-01-01 00:00:01', '2026-01-02 00:00:00'),
                    ('00000000-0000-0000-0000-000000000003', 'Test task3', 'Test description', 'TODO', 'HIGH', 'user1', '2029-01-01 00:00:00', '{tag1, tag2}', '2026-01-01 00:00:02', '2026-01-01 00:00:00');
                """)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest extends BaseIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private MockMvc mockMvc;

  @Test
  void shouldSuccessCreateTask() {
    CreateTaskRequest createTaskRequest = Instancio.of(CreateTaskRequest.class).create();
    var response = restTemplate.postForEntity("/api/v1/tasks", createTaskRequest, Task.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders()).hasFieldOrProperty("Location");
    assertThat(response)
        .extracting(ResponseEntity::getBody)
        .isNotNull()
        .satisfies(
            responseBody -> {
              responseBody.getPriority().equals(createTaskRequest.getPriority());
              assertEquals(createTaskRequest.getTitle(), responseBody.getTitle());
              assertEquals(createTaskRequest.getDescription(), responseBody.getDescription());
              assertEquals(Task.StatusEnum.TODO, responseBody.getStatus());
              assertEquals(
                  Task.PriorityEnum.fromValue(createTaskRequest.getPriority().getValue()),
                  responseBody.getPriority());
              assertEquals(createTaskRequest.getAssignee(), responseBody.getAssignee());
              assertEquals(
                  createTaskRequest.getDueDate().truncatedTo(ChronoUnit.MILLIS),
                  responseBody.getDueDate().truncatedTo(ChronoUnit.MILLIS));
              assertThat(responseBody.getTags())
                  .containsExactly(createTaskRequest.getTags().toArray(new String[0]));
            });

    Optional<TaskEntity> taskOptional = taskRepository.findById(response.getBody().getId());
    TaskEntity taskEntity = taskOptional.get();
    assertThat(taskEntity).isNotNull();
  }

    @Test
    @DisplayName("Should поддерживать фильтрацию и пагинацию")
    void shouldSupportFilteringAndPagination() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/v1/tasks")
                        .param("status", "TODO")
                        .param("priority", "HIGH")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "priority:desc,createdAt:asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks").isNotEmpty())
                .andExpect(jsonPath("$.pagination.page").value(0))
                .andExpect(jsonPath("$.pagination.size").value(10));
    }

  @Test
  @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
  @Sql(statements = "commit;")
  void shouldSuccessUpdateTask() {
    UpdateTaskRequest updateTaskRequest = Instancio.of(UpdateTaskRequest.class).create();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> requestEntity = new HttpEntity<>(updateTaskRequest, headers);
    var path = String.format("/api/v1/tasks/%s", TASK_ID);
    var response = restTemplate.exchange(path, HttpMethod.PUT, requestEntity, Task.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response)
        .extracting(ResponseEntity::getBody)
        .isNotNull()
        .satisfies(
            responseBody -> {
              assertEquals(updateTaskRequest.getTitle(), responseBody.getTitle());
              assertEquals(updateTaskRequest.getDescription(), responseBody.getDescription());
              assertEquals(
                  Task.StatusEnum.fromValue(updateTaskRequest.getStatus().getValue()),
                  responseBody.getStatus());
              assertEquals(
                  Task.PriorityEnum.fromValue(updateTaskRequest.getPriority().getValue()),
                  responseBody.getPriority());
              assertEquals(updateTaskRequest.getAssignee(), responseBody.getAssignee());
              assertEquals(
                  updateTaskRequest.getDueDate().truncatedTo(ChronoUnit.MILLIS),
                  responseBody.getDueDate().truncatedTo(ChronoUnit.MILLIS));
              assertThat(responseBody.getTags())
                  .containsExactly(updateTaskRequest.getTags().toArray(new String[0]));
            });

    Optional<TaskEntity> taskOptional = taskRepository.findById(response.getBody().getId());
    TaskEntity taskEntity = taskOptional.get();
    assertThat(taskEntity).isNotNull();
    assertThat(taskEntity.getUpdatedAt()).isNotEqualTo(taskEntity.getCreatedAt());
  }

  @Test
  @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
  @Sql(statements = "commit;")
  void shouldSuccessPatch() {
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

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> requestEntity = new HttpEntity<>(operations, headers);
    var path = String.format("/api/v1/tasks/%s", TASK_ID);

    var response = restTemplate.patchForObject(path, requestEntity, Object.class);
    assertThat(response).hasFieldOrPropertyWithValue("status", HttpStatus.OK);
  }
}
