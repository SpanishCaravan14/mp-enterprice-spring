package ru.mentee.banking.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "user_login")
  private String userLogin;

  @Column(name = "operation", nullable = false, length = 100)
  private String operation;

  @Column(name = "timestamp", nullable = false)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
      timezone = "UTC")
  private LocalDateTime timestamp;

  @Column(name = "status", nullable = false, length = 20)
  private String status;

  @Column(name = "details", columnDefinition = "TEXT")
  private String details;
}
