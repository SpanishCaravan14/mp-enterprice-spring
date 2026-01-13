package ru.mentee.banking.api.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.service.internal.AccountService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/api/accounts/{accountId}/balance")
    public ResponseEntity<BalanceDto> getBalanceById(@PathVariable @NotNull String accountId) {
        log.info("Trying getBalanceById {}", accountId);
        return ResponseEntity.ok(accountService.getBalanceById(accountId));
    }
}
