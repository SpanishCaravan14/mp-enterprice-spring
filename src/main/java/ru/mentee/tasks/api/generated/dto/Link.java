package ru.mentee.tasks.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import java.util.Objects;

/** Link */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-01-14T17:58:04.597713+03:00[Europe/Moscow]",
    comments = "Generator version: 7.5.0")
public class Link {

  private String href;

  private String method;

  public Link href(String href) {
    this.href = href;
    return this;
  }

  /**
   * Get href
   *
   * @return href
   */
  @Schema(name = "href", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("href")
  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Link method(String method) {
    this.method = method;
    return this;
  }

  /**
   * Get method
   *
   * @return method
   */
  @Schema(name = "method", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("method")
  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return Objects.equals(this.href, link.href) && Objects.equals(this.method, link.method);
  }

  @Override
  public int hashCode() {
    return Objects.hash(href, method);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Link {\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
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
