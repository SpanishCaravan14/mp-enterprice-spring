package ru.mentee.banking.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.annotation.Validatable;
import ru.mentee.banking.domain.model.Account;
import ru.mentee.banking.domain.repository.AccountRepository;

import java.math.BigDecimal;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {
    private final AccountRepository accountRepository;

    @Around("@annotation(validatable)")
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Object validateTransfer(ProceedingJoinPoint proceedingJoinPoint, Validatable validatable) throws Throwable{
        Object[] args = proceedingJoinPoint.getArgs();
        Long fromAccountId = (Long) args[0];
        Long  toAccountId = (Long) args[1];
        BigDecimal amount = (BigDecimal) args [2];

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account fromAccount= accountRepository.findByIdToUpdate(fromAccountId).filter(Account::getActive).orElseThrow(RuntimeException::new);

        if(!fromAccount.getOwnerUser().getUsername().equals(username)){
            throw new RuntimeException("Not the owner of this account");
        }

        if(fromAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("Остаток после перевода не может быть отрицательным");
        }

        Account toAccount = accountRepository.findByIdToUpdate(toAccountId)
                .filter(Account::getActive)
                .orElseThrow(() -> new RuntimeException("Account to not found or not active"));

        if(!fromAccount.getCurrency().equals(toAccount.getCurrency())){
            throw new RuntimeException("Попытка перевода между счетами разного типа валюты");
        }

        log.info("Валидация перевода с аккаунта {} на {} прошла успешно", fromAccountId, toAccountId);
        try{
           Object result = proceedingJoinPoint.proceed();
           return result;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
