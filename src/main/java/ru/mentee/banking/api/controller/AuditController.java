package ru.mentee.banking.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.banking.api.dto.AuditEntryDto;
import ru.mentee.banking.service.internal.AuditService;

@RestController
@RequiredArgsConstructor
public class AuditController {
  private final AuditService auditService;

  @GetMapping("/api/audit/operations")
  public ResponseEntity<List<AuditEntryDto>> getOperations(
      @RequestParam String userId,
      @RequestParam LocalDateTime from,
      @RequestParam LocalDateTime to) {
    return ResponseEntity.ok()
        .header("Description", "Список операций")
        .body(auditService.getAuditReportByTimestampBetween(userId, from, to));
  }
}
