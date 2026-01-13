package ru.mentee.banking.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mentee.banking.api.dto.*;
import ru.mentee.banking.domain.model.Account;
import ru.mentee.banking.domain.model.AuditEntry;

@Mapper(componentModel = "spring")
public interface BankingDtoMapper {

    PaymentRequestDto toPaymentRequestDto(PaymentRequest paymentRequest);
   TransferRequestDto toTransferRequestDto(TransferRequest transferRequest);

    @Mapping(source = "id", target = "accountId")
    BalanceDto toResponseBalanceDto(Account account);
    @Mapping(source = "userLogin", target = "userId")
    AuditEntryDto toResponseAuditEntryDto(AuditEntry auditEntry);
}
