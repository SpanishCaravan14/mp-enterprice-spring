package ru.mentee.tasks.api.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
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

@Mapper(componentModel = "spring")
@Component
public interface TaskMapper {

  @Mapping(target = "tagsArray", source = "tags")
  TaskEntity toEntity(CreateTaskRequest source);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "tagsArray", source = "tags")
  TaskEntity toEntity(UpdateTaskRequest source);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  TaskEntity toPartialEntity(TaskEntity partial, @MappingTarget TaskEntity entity);

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

  default String[] map(List<String> tags) {
    return tags.toArray(new String[0]);
  }

  default List<String> map(String[] tagsArray) {
    return Arrays.asList(tagsArray);
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
