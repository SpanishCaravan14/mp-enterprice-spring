package ru.mentee.banking.aspect;

import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.domain.model.AuditEntry;
import ru.mentee.banking.service.internal.AuditService;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
  private final AuditService auditService;

  @Before("@annotation(auditable)")
  public void logAuditable(JoinPoint joinPoint, Auditable auditable) {
    String operation = auditable.operation();
    log.info("Сработал метод before auditable события {}", operation);
  }

  @Around("@annotation(auditable)")
  public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
    AuditEntry entry = new AuditEntry();
    String action = auditable.operation();
    String user = SecurityContextHolder.getContext().getAuthentication().getName();
    Object[] args = joinPoint.getArgs();
    entry.setOperation(action);
    entry.setUserLogin(user);
    entry.setTimestamp(LocalDateTime.now());
    entry.setDetails(Arrays.toString(args));
    try {
      Object result = joinPoint.proceed();
      entry.setStatus("SUCCESS");
      return result;
    } catch (Exception e) {
      entry.setStatus("FAILED");
      entry.setDetails(entry.getDetails() + " " + e.getMessage());
      throw e;
    } finally {
      auditService.saveAuditEntry(entry);
    }
  }
}
