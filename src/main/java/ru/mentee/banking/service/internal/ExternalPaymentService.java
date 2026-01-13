package ru.mentee.banking.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.annotation.RequiresRole;
import ru.mentee.banking.annotation.Retryable;
import ru.mentee.banking.api.dto.PaymentRequestDto;
import ru.mentee.banking.domain.model.UserRole;
import ru.mentee.banking.service.external.ExternalUnavailableServiceClient;

@Service
@RequiredArgsConstructor
public class ExternalPaymentService {
  private final ExternalUnavailableServiceClient externalUnavailableServiceClient;

  @Retryable(retryCount = 10)
  @RequiresRole({UserRole.PREMIUM_USER, UserRole.ADMIN})
  @Auditable(operation = "external payment")
  public void payExternal(PaymentRequestDto paymentRequestDto) {
    externalUnavailableServiceClient.pay();
  }
}
