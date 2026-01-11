package ru.mentee.banking.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "money_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account accountFrom;
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account accountTo;

    private BigDecimal amount;
    private LocalDateTime timeStamp;
}
