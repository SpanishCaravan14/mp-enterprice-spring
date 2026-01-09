package ru.mentee.library.api.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.domain.model.Book;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private CreateBookRequest createBookRequest;
    private Book book;

    @BeforeEach
    void setUp() {
        createBookRequest = CreateBookRequest.builder()
                .title("Test Title")
                .author("Test Author")
                .isbn("978-5-17-118142-3")
                .publishedDate(LocalDate.of(2024, 1, 1))
                .build();

        book = Book.builder()
                .id(1L)
                .title("Book Title")
                .author("Author")
                .isbn("978-0-306-40615-7")
                .publishedDate(LocalDate.of(2023, 12, 25))
                .available(true)
                .build();
    }

    @Test
    @DisplayName("Корректный маппинг CreateBookRequest в Book")
    void toBookCreateModel_shouldMapAllFields() {
        Book mappedBook = BookMapper.toBookCreateModel(createBookRequest);


        assertThat(mappedBook)
                .hasFieldOrPropertyWithValue("title", createBookRequest.getTitle())
                .hasFieldOrPropertyWithValue("author", createBookRequest.getAuthor())
                .hasFieldOrPropertyWithValue("isbn", createBookRequest.getIsbn())
                .hasFieldOrPropertyWithValue("publishedDate", createBookRequest.getPublishedDate())
                .hasFieldOrPropertyWithValue("available", true);

        assertThat(mappedBook.getId()).isNull();
    }

    @Test
    @DisplayName("Маппинг Book в BookDto")
    void toBookReadModel_shouldMapAllFields() {
        BookDto dto = BookMapper.toBookReadModel(book);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("id", book.getId())
                .hasFieldOrPropertyWithValue("title", book.getTitle())
                .hasFieldOrPropertyWithValue("author", book.getAuthor())
                .hasFieldOrPropertyWithValue("isbn", book.getIsbn())
                .hasFieldOrPropertyWithValue("publishedDate", book.getPublishedDate())
                .hasFieldOrPropertyWithValue("available", book.getAvailable());
    }

    @Test
    @DisplayName("toBookReadModel() с available = false")
    void toBookReadModel_withAvailableFalse() {
        book.setAvailable(false);

        BookDto dto = BookMapper.toBookReadModel(book);

        assertThat(dto.getAvailable()).isFalse();
    }
}
