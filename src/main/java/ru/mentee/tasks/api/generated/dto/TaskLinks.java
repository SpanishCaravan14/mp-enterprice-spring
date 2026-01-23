package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.util.Objects;

/** TaskLinks */
@JsonTypeName("Task__links")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-14T17:58:04.597713+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class TaskLinks {

  private Link self;

  private Link comments;

  private Link assignee;

  public TaskLinks self(Link self) {
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

  public TaskLinks comments(Link comments) {
    this.comments = comments;
    return this;
  }

  /**
   * Get comments
   *
   * @return comments
   */
  @Valid
  @Schema(name = "comments", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comments")
  public Link getComments() {
    return comments;
  }

  public void setComments(Link comments) {
    this.comments = comments;
  }

  public TaskLinks assignee(Link assignee) {
    this.assignee = assignee;
    return this;
  }

  /**
   * Get assignee
   *
   * @return assignee
   */
  @Valid
  @Schema(name = "assignee", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assignee")
  public Link getAssignee() {
    return assignee;
  }

  public void setAssignee(Link assignee) {
    this.assignee = assignee;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskLinks taskLinks = (TaskLinks) o;
    return Objects.equals(this.self, taskLinks.self)
        && Objects.equals(this.comments, taskLinks.comments)
        && Objects.equals(this.assignee, taskLinks.assignee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, comments, assignee);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskLinks {\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
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
