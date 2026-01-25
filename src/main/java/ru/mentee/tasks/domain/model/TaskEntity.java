package ru.mentee.tasks.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {

  @Id
  @UuidGenerator
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "title", nullable = false, length = 200)
  private String title;

  @Column(name = "description", length = 2000)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private TaskStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "priority", nullable = false)
  private TaskPriority priority;

  @Column(name = "assignee")
  private String assignee;

  @Column(name = "due_date", columnDefinition = "TIMESTAMP(6) WITH TIME ZONE")
  private Instant dueDate;

  @Column(name = "tags", columnDefinition = "text[]")
  private String[] tagsArray;

  @CreationTimestamp
  @Column(
      name = "created_at",
      columnDefinition = "TIMESTAMP(6) WITH TIME ZONE",
      nullable = false,
      updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", columnDefinition = "TIMESTAMP(6) WITH TIME ZONE", nullable = false)
  private Instant updatedAt;
}
