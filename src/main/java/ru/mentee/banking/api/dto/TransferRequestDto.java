package ru.mentee.banking.api.dto;

import java.math.BigDecimal;

public record TransferRequestDto(Long fromAccount, Long toAccount, BigDecimal amount) {}
