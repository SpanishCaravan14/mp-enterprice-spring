package ru.mentee.banking.api.mapper;

import org.mapstruct.Mapper;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.domain.model.Account;

@Mapper(componentModel = "spring")
public interface BankingDtoMapper {
    BalanceDto toResponseBalanceDto(Account account);
}
