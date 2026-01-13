package ru.mentee.banking.service.internal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.RequiresRole;
import ru.mentee.banking.api.dto.AuditEntryDto;
import ru.mentee.banking.api.mapper.BankingDtoMapper;
import ru.mentee.banking.domain.model.AuditEntry;
import ru.mentee.banking.domain.model.UserRole;
import ru.mentee.banking.domain.repository.AuditRepository;

@Service
@RequiredArgsConstructor
public class AuditService {
  private final AuditRepository auditRepository;
  private final BankingDtoMapper bankingDtoMapper;

  @Transactional
  @RequiresRole({UserRole.ADMIN})
  public List<AuditEntryDto> getAuditReportByTimestampBetween(
      String userLogin, LocalDateTime from, LocalDateTime to) {
    List<AuditEntry> auditEntryList =
        auditRepository.findByUserLoginAndTimestampBetween(userLogin, from, to);
    return auditEntryList.stream()
        .map(bankingDtoMapper::toResponseAuditEntryDto)
        .collect(Collectors.toList());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveAuditEntry(AuditEntry auditEntry) {
    auditRepository.save(auditEntry);
  }
}
