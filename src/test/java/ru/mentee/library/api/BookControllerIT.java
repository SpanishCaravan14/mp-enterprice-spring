package ru.mentee.library.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class BookControllerIT {

  @Container static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.liquibase.change-log", () -> "classpath:/db/migration/db.changelog.yaml");
  }

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("Создание книги с валидными данными")
  void shouldCreateBookWithValidData() throws Exception {

    String requestJson =
        """
                    {"title": "Test Book", "author": "Test Author", "isbn": "978-3-16-148410-0"}
                """;

    mockMvc
        .perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "/api/books/1"));
  }

  @Test
  @DisplayName("Создание книги с некорректными данными")
  void testBookCreationVithInvalidRequest() throws Exception {
    String requestJson =
        """
                    {"title": "Test Book", "isbn": "978-3-16-148410-0"}
                """;

    mockMvc
        .perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("GET /api/books/{id} — успешное получение книги (HTTP 200)")
  void shouldReturnBook_whenBookExists() throws Exception {
    createBookInDatabase();

    ResultActions result =
        mockMvc.perform(get("/api/books/1").contentType(MediaType.APPLICATION_JSON));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("Test Book"))
        .andExpect(jsonPath("$.author").value("Test Author"))
        .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"))
        .andExpect(jsonPath("$.available").value(true));
  }

  @Test
  @DisplayName("GET /api/books/{id} — книга не найдена (HTTP 404)")
  void shouldReturnNotFound_whenBookDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/api/books/999").contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("GET /api/books/{id} — некорректный ID (null, HTTP 400)")
  void shouldReturnBadRequest_whenIdIsNull() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/api/books/null").contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isBadRequest());
  }

  private void createBookInDatabase() throws Exception {
    String requestJson =
        """
                    {"title": "Test Book", "author": "Test Author", "isbn": "978-3-16-148410-0"}
                """;

    mockMvc
        .perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isCreated());
  }
}
