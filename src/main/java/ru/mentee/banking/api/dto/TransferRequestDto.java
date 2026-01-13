package ru.mentee.banking.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public record TransferRequestDto(
        Long fromAccount,
        Long toAccount,
        BigDecimal amount
) {
}
