package ru.mentee.banking.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        String fromAccount,
        @NotNull
        String toAccount,
        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount) {
}
