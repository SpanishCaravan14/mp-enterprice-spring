package ru.mentee.banking.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.banking.api.dto.TransferRequest;
import ru.mentee.banking.api.dto.TransferResultDto;
import ru.mentee.banking.api.mapper.BankingDtoMapper;
import ru.mentee.banking.domain.model.Transfer;
import ru.mentee.banking.service.TransferService;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    private final BankingDtoMapper bankingDtoMapper;

    @PostMapping("/api/transfers/")
    public ResponseEntity<TransferResultDto> transfer(@RequestBody TransferRequest transferRequest) {
        Transfer transfer = bankingDtoMapper.toTransferCreateCommand(transferRequest);
        TransferResultDto result = transferService.transfer(transfer.getFromAccount(), transfer.getToAccount(), transfer.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
