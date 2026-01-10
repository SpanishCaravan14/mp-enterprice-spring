package ru.mentee.banking.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest (
        @NotNull
        String accountId,
        @NotNull
        String paymentDetails,
        @NotNull
        BigDecimal amount) {}
