package ru.mentee.tasks.domain.search;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

public record SearchInfo(Filter filter, Pageable pageable) {
  @Builder
  public record Filter(String status, String assignee, String priority) {}
}
