package ru.mentee.banking.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mentee.banking.api.dto.AuditEntryDto;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.api.dto.TransferRequest;
import ru.mentee.banking.domain.model.Account;
import ru.mentee.banking.domain.model.AuditEntry;
import ru.mentee.banking.domain.model.Transfer;

@Mapper(componentModel = "spring")
public interface BankingDtoMapper {
    Transfer toTransferCreateCommand(TransferRequest transferRequest);

    @Mapping(source = "id", target = "accountId")
    BalanceDto toResponseBalanceDto(Account account);

    AuditEntryDto toResponseAuditEntryDto(AuditEntry auditEntry);
}
