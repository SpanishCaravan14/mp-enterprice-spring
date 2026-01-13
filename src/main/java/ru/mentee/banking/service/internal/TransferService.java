package ru.mentee.banking.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.RequiresRole;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.annotation.Validatable;
import ru.mentee.banking.api.dto.TransferResultDto;
import ru.mentee.banking.api.dto.TransferStatus;
import ru.mentee.banking.domain.model.Account;
import ru.mentee.banking.domain.model.MoneyTransaction;
import ru.mentee.banking.domain.model.UserRole;
import ru.mentee.banking.domain.repository.AccountRepository;
import ru.mentee.banking.domain.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    @Autowired
    private TransferService transferService;

    @Auditable(operation = "internal transfer")
    @RequiresRole({UserRole.ADMIN, UserRole.USER, UserRole.PREMIUM_USER})
    @Validatable(operation = "internal transfer")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public TransferResultDto transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        Account fromAccount= accountRepository.findByIdToUpdate(fromAccountId).filter(Account::getActive).orElseThrow(RuntimeException::new);

        Account toAccount = accountRepository.findByIdToUpdate(toAccountId)
                .filter(Account::getActive)
                .orElseThrow(() -> new RuntimeException("Account to not found or not active"));

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.saveAll(List.of(fromAccount, toAccount));

        MoneyTransaction moneyTransaction = transferService.saveMoneyTransaction(MoneyTransaction.builder()
                .fromAccountId(fromAccount.getId())
                .toAccountId(toAccount.getId())
                .amount(amount)
                .timeStamp(LocalDateTime.now())
                .build());
        return new TransferResultDto(String.valueOf(moneyTransaction.getId()), TransferStatus.SUCCESS, moneyTransaction.getTimeStamp());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MoneyTransaction saveMoneyTransaction(MoneyTransaction moneyTransaction){
        return transactionRepository.save(moneyTransaction);
    }
}
