package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/** UpdateTaskRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-15T20:30:18.084450+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class UpdateTaskRequest {

  private String title;

  private String description;

  /** Gets or Sets status */
  public enum StatusEnum {
    TODO("TODO"),

    IN_PROGRESS("IN_PROGRESS"),

    DONE("DONE");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  /** Gets or Sets priority */
  public enum PriorityEnum {
    LOW("LOW"),

    MEDIUM("MEDIUM"),

    HIGH("HIGH"),

    CRITICAL("CRITICAL");

    private String value;

    PriorityEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PriorityEnum fromValue(String value) {
      for (PriorityEnum b : PriorityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private PriorityEnum priority;

  private String assignee;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dueDate;

  @Valid private List<String> tags = new ArrayList<>();

  public UpdateTaskRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   *
   * @return title
   */
  @Size(min = 3, max = 200)
  @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UpdateTaskRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
   * @return description
   */
  @Size(max = 2000)
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UpdateTaskRequest status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public UpdateTaskRequest priority(PriorityEnum priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Get priority
   *
   * @return priority
   */
  @Schema(name = "priority", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  public UpdateTaskRequest assignee(String assignee) {
    this.assignee = assignee;
    return this;
  }

  /**
   * Get assignee
   *
   * @return assignee
   */
  @Schema(name = "assignee", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assignee")
  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public UpdateTaskRequest dueDate(OffsetDateTime dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Get dueDate
   *
   * @return dueDate
   */
  @Valid
  @Schema(name = "dueDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dueDate")
  public OffsetDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(OffsetDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public UpdateTaskRequest tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public UpdateTaskRequest addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   *
   * @return tags
   */
  @Schema(name = "tags", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTaskRequest updateTaskRequest = (UpdateTaskRequest) o;
    return Objects.equals(this.title, updateTaskRequest.title)
        && Objects.equals(this.description, updateTaskRequest.description)
        && Objects.equals(this.status, updateTaskRequest.status)
        && Objects.equals(this.priority, updateTaskRequest.priority)
        && Objects.equals(this.assignee, updateTaskRequest.assignee)
        && Objects.equals(this.dueDate, updateTaskRequest.dueDate)
        && Objects.equals(this.tags, updateTaskRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, status, priority, assignee, dueDate, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTaskRequest {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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
