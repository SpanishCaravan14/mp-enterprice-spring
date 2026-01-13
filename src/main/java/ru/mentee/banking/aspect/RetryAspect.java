package ru.mentee.banking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.mentee.banking.annotation.Retryable;

@Aspect
@Component
@Slf4j
public class RetryAspect {
  @Around("@annotation(retryable)")
  public Object proceedForSuccess(ProceedingJoinPoint joinPoint, Retryable retryable)
      throws Throwable {
    int retryCount = retryable.retryCount();
    String operation = retryable.operation();
    for (int i = 0; i < retryCount; i++) {
      try {
        log.info("Попытка #{} вызова retryable {}", i, operation);
        Object result = joinPoint.proceed();
        log.info("Retryable {} завершился успехом на {} попытке", operation, i);
        return result;
      } catch (Throwable t) {
        if (i == retryCount - 1) {
          log.error("Не удалось завершить retryable {} успехом", operation);
          throw t;
        }
      }
    }
    throw new RuntimeException("Ошибка при работе retryable вызова");
  }
}
