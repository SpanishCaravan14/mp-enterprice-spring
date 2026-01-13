package ru.mentee.banking.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "external-service", url = "${external-service.url}")
public interface ExternalUnavailableServiceClient {
    @GetMapping("/api/pay")
    void pay();
}
