package ru.mentee.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.AllowedRole;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.api.dto.TransferResultDto;
import ru.mentee.banking.api.dto.TransferStatus;
import ru.mentee.banking.domain.model.Account;
import ru.mentee.banking.domain.model.MoneyTransaction;
import ru.mentee.banking.domain.model.UserRole;
import ru.mentee.banking.domain.repository.AccountRepository;
import ru.mentee.banking.domain.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @AllowedRole({UserRole.ADMIN, UserRole.PREMIUM_USER})
    @Auditable
    public TransferResultDto transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Account account = accountRepository.findById(fromAccountId).orElseThrow(RuntimeException::new);
        if(!account.getOwnerUser().getUsername().equals(username)){
            throw new RuntimeException("Not the owner of this account");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account fromAccount = accountRepository.findByIdToUpdate(fromAccountId)
                .filter(Account::getActive)
                .orElseThrow(() -> new RuntimeException("Account from not found or not active"));

        Account toAccount = accountRepository.findByIdToUpdate(toAccountId)
                .filter(Account::getActive)
                .orElseThrow(() -> new RuntimeException("Account to not found or not active"));

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        MoneyTransaction moneyTransaction = transactionRepository.save(MoneyTransaction.builder()
                .accountFrom(fromAccount)
                .accountTo(toAccount)
                .amount(amount)
                .timeStamp(LocalDateTime.now())
                .build());
        return new TransferResultDto(moneyTransaction.getId().toString(), TransferStatus.SUCCESS, moneyTransaction.getTimeStamp());
    }
}
