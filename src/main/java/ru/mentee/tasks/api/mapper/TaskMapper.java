package ru.mentee.tasks.api.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.mentee.tasks.api.generated.dto.CreateTaskRequest;
import ru.mentee.tasks.api.generated.dto.Task;
import ru.mentee.tasks.api.generated.dto.UpdateTaskRequest;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.domain.model.TaskPriority;
import ru.mentee.tasks.domain.model.TaskStatus;
import ru.mentee.tasks.domain.search.SearchInfo;
import ru.mentee.tasks.domain.search.SearchTask;

@Mapper(componentModel = "spring")
@Component
public interface TaskMapper {

  @Mapping(target = "tagsArray", source = "tags")
  TaskEntity toModel(CreateTaskRequest source);

  @Mapping(target = "tagsArray", source = "tags")
  TaskEntity toModel(UpdateTaskRequest source);

  @Mapping(target = "tags", source = "tagsArray")
  Task toDto(TaskEntity entity);

  @Mapping(target = "filter", expression = "java(toFilter(status, assignee, priority))")
  @Mapping(target = "pageable", expression = "java(toPageable(sort, page, size))")
  SearchInfo toSearchInfo(
      String status, String assignee, String priority, String sort, Integer page, Integer size);

  List<Task> toTasks(List<TaskEntity> taskEntities);

  default OffsetDateTime map(Instant value) {
    return value != null ? value.atOffset(ZoneOffset.UTC) : null;
  }

  default Instant map(OffsetDateTime value) {
    return value != null ? value.toInstant() : null;
  }

  default String[] mapTagsListToArray(List<String> tags) {
    return tags.toArray(new String[0]);
  }

  default List<String> mapTagsArrayToList(String[] tagsArray) {
    return Arrays.asList(tagsArray);
  }

  default LocalDateTime mapOffsetLocal(OffsetDateTime offsetDateTime) {
    return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
  }

  default OffsetDateTime mapLocalOffset(LocalDateTime localDateTime) {
    return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
  }

  default TaskStatus mapSourceStatus(Task.StatusEnum status) {
    return status != null ? TaskStatus.valueOf(status.name()) : null;
  }

  default TaskPriority mapSourcePriority(Task.PriorityEnum priority) {
    return priority != null ? TaskPriority.valueOf(priority.name()) : null;
  }

  default Task.StatusEnum mapModelStatus(TaskStatus status) {
    return status != null ? Task.StatusEnum.fromValue(status.name()) : null;
  }

  default Task.PriorityEnum mapModelPriority(TaskPriority priority) {
    return priority != null ? Task.PriorityEnum.fromValue(priority.name()) : null;
  }

  default SearchInfo.Filter toFilter(String status, String assignee, String priority) {
    return SearchInfo.Filter.builder().status(status).priority(priority).assignee(assignee).build();
  }

  default SearchTask toSearchTask(Page<TaskEntity> taskEntityPage) {
    return new SearchTask(taskEntityPage.getTotalElements(), toTasks(taskEntityPage.getContent()));
  }

  default Pageable toPageable(String sort, Integer page, Integer size) {
    int pageNumber = page != null ? page : 0;
    int pageSize = size != null ? Math.min(size, 100) : 20;

    Sort sortObj = parseSortParameter(sort);
    return PageRequest.of(pageNumber, pageSize, sortObj);
  }

  private Sort parseSortParameter(String sort) {
    if (sort == null || sort.trim().isEmpty()) {
      return Sort.by(Sort.Order.desc("createdAt"));
    }

    try {
      List<Sort.Order> orders = new ArrayList<>();
      String[] sortParams = sort.split(",");

      for (String param : sortParams) {
        String[] parts = param.split(":");
        if (parts.length == 2) {
          String field = parts[0].trim();
          String direction = parts[1].trim().toLowerCase();

          Sort.Order order =
              "desc".equals(direction) ? Sort.Order.desc(field) : Sort.Order.asc(field);
          orders.add(order);
        }
      }

      return orders.isEmpty() ? Sort.by(Sort.Order.desc("createdAt")) : Sort.by(orders);
    } catch (Exception e) {
      return Sort.by(Sort.Order.desc("createdAt"));
    }
  }
}
