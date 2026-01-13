package ru.mentee.banking.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mentee.banking.domain.model.AuditEntry;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntry, Long> {

  @Query(
      "SELECT a FROM AuditEntry a "
          + "WHERE a.timestamp BETWEEN :startDate AND :endDate "
          + "AND a.userLogin = :userLogin "
          + "ORDER BY a.timestamp DESC")
  List<AuditEntry> findByUserLoginAndTimestampBetween(
      String userLogin, LocalDateTime startDate, LocalDateTime endDate);
}
