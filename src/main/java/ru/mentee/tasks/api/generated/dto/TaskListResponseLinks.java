package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.util.Objects;

/** TaskListResponseLinks */
@JsonTypeName("TaskListResponse__links")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-14T17:58:04.597713+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class TaskListResponseLinks {

  private Link self;

  private Link next;

  private Link prev;

  public TaskListResponseLinks self(Link self) {
    this.self = self;
    return this;
  }

  /**
   * Get self
   *
   * @return self
   */
  @Valid
  @Schema(name = "self", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("self")
  public Link getSelf() {
    return self;
  }

  public void setSelf(Link self) {
    this.self = self;
  }

  public TaskListResponseLinks next(Link next) {
    this.next = next;
    return this;
  }

  /**
   * Get next
   *
   * @return next
   */
  @Valid
  @Schema(name = "next", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("next")
  public Link getNext() {
    return next;
  }

  public void setNext(Link next) {
    this.next = next;
  }

  public TaskListResponseLinks prev(Link prev) {
    this.prev = prev;
    return this;
  }

  /**
   * Get prev
   *
   * @return prev
   */
  @Valid
  @Schema(name = "prev", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prev")
  public Link getPrev() {
    return prev;
  }

  public void setPrev(Link prev) {
    this.prev = prev;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskListResponseLinks taskListResponseLinks = (TaskListResponseLinks) o;
    return Objects.equals(this.self, taskListResponseLinks.self)
        && Objects.equals(this.next, taskListResponseLinks.next)
        && Objects.equals(this.prev, taskListResponseLinks.prev);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, next, prev);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskListResponseLinks {\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    next: ").append(toIndentedString(next)).append("\n");
    sb.append("    prev: ").append(toIndentedString(prev)).append("\n");
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
