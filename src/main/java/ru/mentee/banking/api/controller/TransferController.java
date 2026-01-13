package ru.mentee.banking.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.banking.api.dto.*;
import ru.mentee.banking.api.mapper.BankingDtoMapper;
import ru.mentee.banking.service.internal.ExternalPaymentService;
import ru.mentee.banking.service.internal.TransferService;

@RestController
@RequiredArgsConstructor
public class TransferController {
  private final TransferService transferService;
  private final ExternalPaymentService externalPaymentService;
  private final BankingDtoMapper bankingDtoMapper;

  @PostMapping("/api/transfers")
  public ResponseEntity<TransferResultDto> transfer(@RequestBody TransferRequest transferRequest) {
    TransferRequestDto transferRequestDto = bankingDtoMapper.toTransferRequestDto(transferRequest);
    TransferResultDto result =
        transferService.transfer(
            transferRequestDto.fromAccount(),
            transferRequestDto.toAccount(),
            transferRequestDto.amount());
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PostMapping("/api/payments")
  public ResponseEntity<Void> pay(@RequestBody PaymentRequest paymentRequest) {
    PaymentRequestDto paymentRequestDto = bankingDtoMapper.toPaymentRequestDto(paymentRequest);
    externalPaymentService.payExternal(paymentRequestDto);
    return ResponseEntity.ok().build();
  }
}
