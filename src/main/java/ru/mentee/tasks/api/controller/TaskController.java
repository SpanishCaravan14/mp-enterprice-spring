package ru.mentee.tasks.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.api.generated.controller.TasksApi;
import ru.mentee.api.generated.dto.CreateTaskRequest;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.api.generated.dto.Task;
import ru.mentee.api.generated.dto.TaskListResponse;
import ru.mentee.api.generated.dto.UpdateTaskRequest;
import ru.mentee.tasks.api.mapper.TaskMapper;
import ru.mentee.tasks.domain.search.SearchInfo;
import ru.mentee.tasks.service.TaskService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TaskController implements TasksApi {
  private final TaskService taskService;
  private final TaskMapper mapper;

  @Override
  public ResponseEntity<Task> createTask(CreateTaskRequest dto) {
    var model = mapper.toEntity(dto);
    var created = taskService.createTask(model);
    URI location = URI.create("/api/v1/tasks/" + created.getId());
    return ResponseEntity.created(location).body(created);
  }

  @Override
  public ResponseEntity<Task> getTaskById(UUID taskId) {
    return ResponseEntity.ok(taskService.searchTaskById(taskId));
  }

  @Override
  public ResponseEntity<TaskListResponse> getTasks(
      String status, String assignee, String priority, String sort, Integer page, Integer size) {
    SearchInfo searchInfo = mapper.toSearchInfo(status, assignee, priority, sort, page, size);
    return ResponseEntity.ok(taskService.searchTasks(searchInfo));
  }

  @Override
  public ResponseEntity<Task> updateTask(UUID taskId, UpdateTaskRequest updateTaskRequest) {
    var model = mapper.toEntity(updateTaskRequest);
    return ResponseEntity.ok(taskService.updateTask(taskId, model));
  }

  @Override
  public ResponseEntity<Void> patchTask(
      UUID taskId, List<@Valid JsonPatchOperation> jsonPatchOperation) {
    taskService.patchTask(taskId, jsonPatchOperation);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteTask(UUID taskId) {
    taskService.deleteTask(taskId);
    return ResponseEntity.noContent().build();
  }
}
