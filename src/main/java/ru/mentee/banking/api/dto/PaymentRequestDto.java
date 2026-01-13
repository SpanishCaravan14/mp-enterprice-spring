package ru.mentee.banking.api.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(String accountId,
                                String paymentDetails,
                                BigDecimal amount) {
}
