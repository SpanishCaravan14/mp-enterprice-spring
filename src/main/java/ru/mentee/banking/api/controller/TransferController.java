package ru.mentee.banking.api.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.banking.api.dto.BalanceDto;
import ru.mentee.banking.service.AccountService;

@RestController
@RequiredArgsConstructor
public class TransferController {


}
