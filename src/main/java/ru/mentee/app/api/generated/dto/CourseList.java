package ru.mentee.app.api.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * CourseList
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T16:31:21.917251+03:00[Europe/Moscow]", comments = "Generator version: 7.5.0")
public class CourseList {

  @Valid
  private List<@Valid Course> courses = new ArrayList<>();

  private Integer totalCount;

  public CourseList courses(List<@Valid Course> courses) {
    this.courses = courses;
    return this;
  }

  public CourseList addCoursesItem(Course coursesItem) {
    if (this.courses == null) {
      this.courses = new ArrayList<>();
    }
    this.courses.add(coursesItem);
    return this;
  }

  /**
   * Get courses
   * @return courses
  */
  @Valid 
  @Schema(name = "courses", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("courses")
  public List<@Valid Course> getCourses() {
    return courses;
  }

  public void setCourses(List<@Valid Course> courses) {
    this.courses = courses;
  }

  public CourseList totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * Get totalCount
   * @return totalCount
  */
  
  @Schema(name = "totalCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalCount")
  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseList courseList = (CourseList) o;
    return Objects.equals(this.courses, courseList.courses) &&
        Objects.equals(this.totalCount, courseList.totalCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(courses, totalCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CourseList {\n");
    sb.append("    courses: ").append(toIndentedString(courses)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

