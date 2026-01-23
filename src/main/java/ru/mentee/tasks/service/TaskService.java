package ru.mentee.tasks.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.tasks.api.exception.TaskNotFoundException;
import ru.mentee.tasks.api.generated.dto.JsonPatchOperation;
import ru.mentee.tasks.api.generated.dto.Task;
import ru.mentee.tasks.api.generated.dto.TaskListResponse;
import ru.mentee.tasks.api.mapper.TaskMapper;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.domain.model.TaskPriority;
import ru.mentee.tasks.domain.model.TaskStatus;
import ru.mentee.tasks.domain.repository.TaskRepository;
import ru.mentee.tasks.domain.search.SearchInfo;
import ru.mentee.tasks.domain.specification.TaskSpecification;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @Transactional
  public Task createTask(TaskEntity model) {
    model.setStatus(TaskStatus.TODO);
    return taskMapper.toDto(taskRepository.save(model));
  }

  @Transactional
  public Task getTaskById(UUID id) {
    return taskRepository
        .findById(id)
        .map(taskMapper::toDto)
        .orElseThrow(TaskNotFoundException::new);
  }

  @Transactional
  public TaskListResponse getAllTasks(SearchInfo searchInfo) {
    Specification<TaskEntity> productSpecification =
        TaskSpecification.buildSpecification(searchInfo);
    var result = taskRepository.findAll(productSpecification, searchInfo.pageable());
    TaskListResponse response = new TaskListResponse();
    response.setTotalElements(result.getTotalElements());
    response.setTasks(result.getContent().stream().map(taskMapper::toDto).toList());
    return response;
  }

  @Transactional
  public Task updateTask(UUID taskId, TaskEntity update) {
    TaskEntity current = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    applyUpdate(current, update);
    current.setUpdatedAt(Instant.now());
    return taskMapper.toDto(taskRepository.save(current));
  }

  @Transactional
  public Task patchTask(UUID taskId, List<JsonPatchOperation> patchOperations) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    applyPatch(task, patchOperations);
    task.setUpdatedAt(Instant.now());
    return taskMapper.toDto(taskRepository.save(task));
  }

  @Transactional
  public void deleteTask(UUID taskId) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    taskRepository.delete(task);
  }

  private void applyPatch(TaskEntity current, List<JsonPatchOperation> patchOperations) {
    for (JsonPatchOperation patchOperation : patchOperations) {
      switch (patchOperation.getOp()) {
        case REPLACE:
          applyReplaceOperation(current, patchOperation);
        case ADD:
          applyAddOperation(current, patchOperation);
        case REMOVE:
          applyRemoveOperation(current, patchOperation);
        default:
          throw new IllegalArgumentException("Invalid operation: " + patchOperation.getOp());
      }
    }
  }

  private void applyAddOperation(TaskEntity current, JsonPatchOperation patchOperation) {
    String path = patchOperation.getPath();
    String value = patchOperation.getValue();

    switch (path) {
      case "/tags":
        addTag(current, value);
        break;
      default:
        throw new IllegalArgumentException("Invalid path: " + path);
    }
  }

  private void applyRemoveOperation(TaskEntity current, JsonPatchOperation patchOperation) {
    String path = patchOperation.getPath();
    String value = patchOperation.getValue();

    switch (path) {
      case "/tags":
        removeTag(current, value);
        break;
      default:
        throw new IllegalArgumentException("Invalid path: " + path);
    }
  }

  private void applyReplaceOperation(TaskEntity current, JsonPatchOperation patchOperation) {
    String path = patchOperation.getPath();
    String value = patchOperation.getValue();

    switch (path) {
      case "/title":
        current.setTitle(value);
        break;
      case "/description":
        current.setDescription(value);
        break;
      case "/status":
        current.setStatus(TaskStatus.valueOf(value));
        break;
      case "/priority":
        current.setPriority(TaskPriority.valueOf(value));
        break;
      case "/assignee":
        current.setAssignee(value);
        break;
      case "/dueDate":
        current.setDueDate(Instant.parse(value));
        break;
      default:
        throw new IllegalArgumentException("Invalid path: " + path);
    }
  }

  private void applyUpdate(TaskEntity task, TaskEntity update) {
    task.setTitle(update.getTitle());
    task.setDescription(update.getDescription());
    task.setStatus(TaskStatus.valueOf(update.getStatus().name()));
    task.setPriority(TaskPriority.valueOf(update.getPriority().name()));
    task.setAssignee(update.getAssignee());
    task.setDueDate(update.getDueDate());
    task.setTagsArray(update.getTagsArray());
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

  private void removeTag(TaskEntity current, String tag) {
    if (current.getTagsArray() != null) {
      current.setTagsArray(
          Arrays.stream(current.getTagsArray()).filter(t -> !t.equals(tag)).toArray(String[]::new));
    }
  }
}
