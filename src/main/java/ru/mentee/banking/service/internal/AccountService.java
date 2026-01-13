package ru.mentee.banking.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.RequiresRole;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.annotation.Cacheable;
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
    @Auditable(operation = "balance check")
    @RequiresRole({UserRole.ADMIN, UserRole.USER, UserRole.PREMIUM_USER})
    @Cacheable
    public BalanceDto getBalanceById(String accountId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Account account = accountRepository.findById(Long.valueOf(accountId)).orElseThrow(() -> new RuntimeException("Счет не найден"));
        if(!account.getOwnerUser().getUsername().equals(username)){
            throw new RuntimeException("Not the owner of this account");
        }
        return bankingDtoMapper.toResponseBalanceDto(account);
    };
}
