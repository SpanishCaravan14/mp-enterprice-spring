package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/** GetTaskById404Response */
@JsonTypeName("getTaskById_404_response")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-15T20:30:18.084450+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class GetTaskById404Response {

  private String message;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  private Integer status;

  public GetTaskById404Response message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message
   */
  @Schema(name = "message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public GetTaskById404Response timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   *
   * @return timestamp
   */
  @Valid
  @Schema(name = "timestamp", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timestamp")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public GetTaskById404Response status(Integer status) {
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
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetTaskById404Response getTaskById404Response = (GetTaskById404Response) o;
    return Objects.equals(this.message, getTaskById404Response.message)
        && Objects.equals(this.timestamp, getTaskById404Response.timestamp)
        && Objects.equals(this.status, getTaskById404Response.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, timestamp, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTaskById404Response {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
