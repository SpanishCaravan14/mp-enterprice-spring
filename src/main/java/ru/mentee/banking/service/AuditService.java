package ru.mentee.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.AllowedRole;
import ru.mentee.banking.api.dto.AuditEntryDto;
import ru.mentee.banking.api.mapper.BankingDtoMapper;
import ru.mentee.banking.domain.model.AuditEntry;
import ru.mentee.banking.domain.model.UserRole;
import ru.mentee.banking.domain.repository.AuditRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final BankingDtoMapper bankingDtoMapper;

    @Transactional
    @AllowedRole(UserRole.ADMIN)
    public List<AuditEntryDto> getAuditReport() {
        List<AuditEntry> auditEntryList = auditRepository.findAll();
        return auditEntryList.stream().map(bankingDtoMapper::toResponseAuditEntryDto).collect(Collectors.toList());
    }
}
