package ru.mentee.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mentee.library.domain.model.Book;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class FictionBookServiceIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.change-log", () -> "classpath:/db/migration/db.changelog.yaml");
    }

    @Autowired
    @Qualifier("fiction")
    private BookService bookService;

    @Test
    @DisplayName("Создание книги с валидными данными")
    void shouldCreateBookWithValidData() throws Exception {

        Book expectedBook = new Book();
        expectedBook.setTitle("Book Title");
        expectedBook.setAuthor("Author");
        expectedBook.setIsbn("978-5-17-118142-3");
        expectedBook.setPublishedDate(LocalDate.now());
        expectedBook.setAvailable(true);

        Book actualBook = bookService.createBook(expectedBook);

        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("title", "Book Title")
                .hasFieldOrPropertyWithValue("author", "Author")
                .hasFieldOrPropertyWithValue("isbn", "978-5-17-118142-3")
                .hasFieldOrPropertyWithValue("publishedDate", LocalDate.now())
                .hasFieldOrPropertyWithValue("available", true)
                .hasFieldOrPropertyWithValue("id", 1L);
    }
}

