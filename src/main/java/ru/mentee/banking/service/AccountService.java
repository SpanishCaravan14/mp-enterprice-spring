package ru.mentee.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.AllowedRole;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.api.mapper.BankingDtoMapper;
import ru.mentee.banking.domain.model.Account;
import ru.mentee.banking.domain.model.UserRole;
import ru.mentee.banking.domain.repository.AccountRepository;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankingDtoMapper bankingDtoMapper;

    @Transactional
    @AllowedRole({UserRole.ADMIN, UserRole.USER, UserRole.PREMIUM_USER})
    @Auditable(operation = "balance_check")
    public BalanceDto getBalanceById(Long accountId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Счет не найден"));
        if(!account.getOwnerUser().getUsername().equals(username)){
            throw new RuntimeException("Not the owner of this account");
        }
        return bankingDtoMapper.toResponseBalanceDto(account);
    };
}
