package ru.mentee.banking.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class Transfer {
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
}
