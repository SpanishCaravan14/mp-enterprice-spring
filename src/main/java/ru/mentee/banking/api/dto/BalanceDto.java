package ru.mentee.banking.api.dto;

import java.math.BigDecimal;

public record BalanceDto(String accountId, BigDecimal amount, String currency) {}
