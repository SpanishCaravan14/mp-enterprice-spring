package ru.mentee.banking.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record AuditEntryDto(
    String id,
    String userId,
    String operation,
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
            timezone = "UTC")
        LocalDateTime timestamp,
    String status,
    Object details) {}
