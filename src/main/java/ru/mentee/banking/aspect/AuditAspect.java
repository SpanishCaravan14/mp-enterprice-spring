package ru.mentee.banking.aspect;

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
import ru.mentee.banking.domain.repository.AuditRepository;
import ru.mentee.banking.service.AuditService;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private final AuditRepository auditRepository;

    @Before("@annotation(auditable)")
    public void logAuditable(JoinPoint joinPoint, Auditable auditable) {
        String operation = auditable.operation();
        log.info("Сработал метод before auditable события {}", operation);
    }

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint,
                        Auditable auditable) throws Throwable {

        String action = auditable.operation();
        String user = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Object[] args = joinPoint.getArgs();

        AuditEntry entry = new AuditEntry();
        entry.setOperation(action);
    //    entry.setUserId(user);
        entry.setTimestamp(LocalDateTime.now());
        entry.setDetails(Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            entry.setStatus("SUCCESS");
            return result;
        } catch (Exception e) {
            entry.setStatus("FAILED");
            throw e;
        } finally {
            auditRepository.save(entry);
        }
    }
}
