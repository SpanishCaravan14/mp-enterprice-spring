package ru.mentee.banking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mentee.banking.domain.model.MoneyTransaction;

public interface TransactionRepository extends JpaRepository<MoneyTransaction, Long> {}
