package ru.mentee.tasks.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.api.generated.dto.Task;
import ru.mentee.api.generated.dto.TaskListResponse;
import ru.mentee.api.generated.dto.TaskListResponsePagination;
import ru.mentee.tasks.api.exception.TaskNotFoundException;
import ru.mentee.tasks.api.mapper.TaskMapper;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.domain.model.TaskStatus;
import ru.mentee.tasks.domain.repository.TaskRepository;
import ru.mentee.tasks.domain.search.SearchInfo;
import ru.mentee.tasks.domain.specification.TaskSpecification;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;
  private final TaskPatchService taskPatchService;

  @Transactional
  public Task createTask(TaskEntity model) {
    model.setStatus(TaskStatus.TODO);
    return taskMapper.toDto(taskRepository.save(model));
  }

  @Transactional
  public Task searchTaskById(UUID id) {
    return taskRepository
        .findById(id)
        .map(taskMapper::toDto)
        .orElseThrow(TaskNotFoundException::new);
  }

  @Transactional
  public TaskListResponse searchTasks(SearchInfo searchInfo) {
    Specification<TaskEntity> productSpecification =
        TaskSpecification.buildSpecification(searchInfo);
    var result = taskRepository.findAll(productSpecification, searchInfo.pageable());
    TaskListResponse response = new TaskListResponse();
    response.setTotalElements(result.getTotalElements());
    response.setTasks(result.getContent().stream().map(taskMapper::toDto).toList());
    response.setPagination(new TaskListResponsePagination()
            .page(result.getPageable().getPageNumber())
            .size(result.getPageable().getPageSize()));
    return response;
  }

  @Transactional
  public Task updateTask(UUID taskId, TaskEntity update) {
    TaskEntity current = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    TaskEntity updated = taskMapper.toPartialEntity(update, current);
    return taskMapper.toDto(taskRepository.save(updated));
  }

  @Transactional
  @SneakyThrows
  public Task patchTask(UUID taskId, List<JsonPatchOperation> patchOperations) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    taskPatchService.patch(task, patchOperations);
    return taskMapper.toDto(taskRepository.save(task));
  }

  @Transactional
  public void deleteTask(UUID taskId) {
    var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    taskRepository.delete(task);
  }
}
