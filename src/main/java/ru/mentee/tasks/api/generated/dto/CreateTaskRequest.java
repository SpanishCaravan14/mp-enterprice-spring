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

/** CreateTaskRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-15T20:30:18.084450+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class CreateTaskRequest {

  private String title;

  private String description;

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

  public CreateTaskRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTaskRequest(String title, PriorityEnum priority) {
    this.title = title;
    this.priority = priority;
  }

  public CreateTaskRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   *
   * @return title
   */
  @NotNull
  @Size(min = 3, max = 200)
  @Schema(name = "title", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public CreateTaskRequest description(String description) {
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

  public CreateTaskRequest priority(PriorityEnum priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Get priority
   *
   * @return priority
   */
  @NotNull
  @Schema(name = "priority", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  public CreateTaskRequest assignee(String assignee) {
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

  public CreateTaskRequest dueDate(OffsetDateTime dueDate) {
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

  public CreateTaskRequest tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public CreateTaskRequest addTagsItem(String tagsItem) {
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
    CreateTaskRequest createTaskRequest = (CreateTaskRequest) o;
    return Objects.equals(this.title, createTaskRequest.title)
        && Objects.equals(this.description, createTaskRequest.description)
        && Objects.equals(this.priority, createTaskRequest.priority)
        && Objects.equals(this.assignee, createTaskRequest.assignee)
        && Objects.equals(this.dueDate, createTaskRequest.dueDate)
        && Objects.equals(this.tags, createTaskRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, priority, assignee, dueDate, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTaskRequest {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
