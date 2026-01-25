package ru.mentee.tasks.domain.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mentee.tasks.domain.model.TaskEntity;
import ru.mentee.tasks.domain.model.TaskEntity_;
import ru.mentee.tasks.domain.search.SearchInfo;

@UtilityClass
public class TaskSpecification {
  public Specification<TaskEntity> buildSpecification(SearchInfo searchInfo) {
    if (searchInfo == null || searchInfo.filter() == null) {
      return Specification.where(null);
    }

    return Specification.where(hasStatus(searchInfo.filter().status()))
        .and(hasAssignee(searchInfo.filter().assignee()))
        .and(hasPriority(searchInfo.filter().priority()));
  }

  public Specification<TaskEntity> hasStatus(String status) {
    return (root, query, criteriaBuilder) ->
        status != null ? criteriaBuilder.equal(root.get(TaskEntity_.status), status) : null;
  }

  public Specification<TaskEntity> hasAssignee(String assignee) {
    return (root, query, criteriaBuilder) ->
        assignee != null
            ? criteriaBuilder.equal(root.get(TaskEntity_.assignee.getName()), assignee)
            : null;
  }

  public Specification<TaskEntity> hasPriority(String priority) {
    return (root, query, criteriaBuilder) ->
        priority != null
            ? criteriaBuilder.equal(root.get(TaskEntity_.priority.getName()), priority)
            : null;
  }
}
