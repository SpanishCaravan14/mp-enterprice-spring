package ru.mentee.banking.aspect;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.api.dto.TransferResultDto;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CachingAspect {
  private final HashMap<String, BalanceDto> balanceMap = new HashMap<>();

  @Around(
      "execution(* ru.mentee.banking.service.internal.AccountService.getBalanceById(String)) && args(accountId)")
  public Object getBalanceFromCache(ProceedingJoinPoint joinpoint, String accountId)
      throws Throwable {
    try {
      if (balanceMap.containsKey(accountId)) {
        log.info("Возвращаем dto баланса для accountId {} из кеша", accountId);
        return balanceMap.get(accountId);
      } else {
        var result = (BalanceDto) joinpoint.proceed();
        balanceMap.put(result.accountId(), result);
        log.info("Dto баланса для accountId {} помещено в кеш", accountId);
        return result;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @AfterReturning(
      pointcut = "execution(* ru.mentee.banking.service.internal.TransferService.transfer(..))",
      returning = "result")
  public void updateCacheAfterTransfer(JoinPoint joinPoint, TransferResultDto result) {
    Object[] args = joinPoint.getArgs();
    String fromAccountId = String.valueOf(args[0]);
    String toAccountId = String.valueOf(args[1]);

    invalidateOutdatedCacheForId(fromAccountId, toAccountId);
    log.info("Значение кеша для аккаунтов id0: {} id1: {} обновлено", fromAccountId, toAccountId);
  }

  private void invalidateOutdatedCacheForId(String... accountIds) {
    for (String accountId : accountIds) {
      try {
        balanceMap.remove(accountId);
      } catch (Exception e) {
        log.error("Ошибка при очистке кэша баланса для аккаунта {}", accountId, e);
        throw new RuntimeException(e);
      }
    }
  }
}
