package tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mentee.banking.domain.repository.AccountRepository;
import ru.mentee.banking.service.internal.AccountService;
import ru.mentee.banking.service.internal.TransferService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TransferServiceTest.class, AccountRepository.class, AccountService.class})
@Testcontainers
@ActiveProfiles("test")
class TransferServiceTest {
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.liquibase.change-log", () -> "classpath:/db/migration/db.changelog.yaml");
//    }
    @Autowired
    TransferService transferService;
    @Test
    void transfer() {
        transferService.transfer(1L, 2L, BigDecimal.valueOf(424.4));
    }
}