package ru.mentee.banking.aspect;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.mentee.banking.annotation.RequiresRole;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

  @Around("@annotation(requiresRole)")
  public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole)
      throws Throwable {

    var currentUserRole = getCurrentUserRole();
    if (Arrays.stream(requiresRole.value()).map(Enum::name).toList().contains(currentUserRole)) {
      try {
        return joinPoint.proceed();
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    } else {
      log.error(
          "Среди допустимых ролей {} нет предоставленной {}",
          requiresRole.value(),
          currentUserRole);
      throw new RuntimeException("You do not have permission to access this resource");
    }
  }

  private String getCurrentUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new RuntimeException("User not authenticated");
    }
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(role -> role.replace("ROLE_", ""))
        .findAny()
        .orElseThrow(() -> new RuntimeException("User has no roles"));
  }
}
