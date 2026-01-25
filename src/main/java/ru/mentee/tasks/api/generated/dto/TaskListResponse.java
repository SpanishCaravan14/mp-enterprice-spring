package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** TaskListResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-15T20:35:22.763697+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class TaskListResponse {

  @Valid private List<@Valid Task> tasks = new ArrayList<>();

  private Long totalElements = null;

  public TaskListResponse tasks(List<@Valid Task> tasks) {
    this.tasks = tasks;
    return this;
  }

  public TaskListResponse addTasksItem(Task tasksItem) {
    if (this.tasks == null) {
      this.tasks = new ArrayList<>();
    }
    this.tasks.add(tasksItem);
    return this;
  }

  /**
   * Get tasks
   *
   * @return tasks
   */
  @Valid
  @Schema(name = "tasks", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tasks")
  public List<@Valid Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<@Valid Task> tasks) {
    this.tasks = tasks;
  }

  public TaskListResponse totalElements(Long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  /**
   * Get totalElements
   *
   * @return totalElements
   */
  @Schema(name = "totalElements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalElements")
  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskListResponse taskListResponse = (TaskListResponse) o;
    return Objects.equals(this.tasks, taskListResponse.tasks)
        && Objects.equals(this.totalElements, taskListResponse.totalElements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tasks, totalElements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskListResponse {\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
