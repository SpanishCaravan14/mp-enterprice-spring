package ru.mentee.banking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.api.mapper.BankingDtoMapper;
import ru.mentee.banking.domain.repository.AccountRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankingDtoMapper bankingDtoMapper;

    public BalanceDto getBalanceById(Long accountId){
        return bankingDtoMapper.toResponseBalanceDto(accountRepository.findById(accountId));
    };
}
