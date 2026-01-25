package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.Objects;

/** JsonPatchOperation */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-15T20:30:18.084450+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class JsonPatchOperation {

  /** Gets or Sets op */
  public enum OpEnum {
    REPLACE("replace"),

    ADD("add"),

    REMOVE("remove");

    private String value;

    OpEnum(String value) {
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
    public static OpEnum fromValue(String value) {
      for (OpEnum b : OpEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private OpEnum op;

  private String path;

  private String value;

  private String from;

  public JsonPatchOperation() {
    super();
  }

  /** Constructor with only required parameters */
  public JsonPatchOperation(OpEnum op, String path) {
    this.op = op;
    this.path = path;
  }

  public JsonPatchOperation op(OpEnum op) {
    this.op = op;
    return this;
  }

  /**
   * Get op
   *
   * @return op
   */
  @NotNull
  @Schema(name = "op", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("op")
  public OpEnum getOp() {
    return op;
  }

  public void setOp(OpEnum op) {
    this.op = op;
  }

  public JsonPatchOperation path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   *
   * @return path
   */
  @NotNull
  @Schema(name = "path", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public JsonPatchOperation value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   *
   * @return value
   */
  @Schema(name = "value", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public JsonPatchOperation from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   *
   * @return from
   */
  @Schema(name = "from", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("from")
  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonPatchOperation jsonPatchOperation = (JsonPatchOperation) o;
    return Objects.equals(this.op, jsonPatchOperation.op)
        && Objects.equals(this.path, jsonPatchOperation.path)
        && Objects.equals(this.value, jsonPatchOperation.value)
        && Objects.equals(this.from, jsonPatchOperation.from);
  }

  @Override
  public int hashCode() {
    return Objects.hash(op, path, value, from);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonPatchOperation {\n");
    sb.append("    op: ").append(toIndentedString(op)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
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
