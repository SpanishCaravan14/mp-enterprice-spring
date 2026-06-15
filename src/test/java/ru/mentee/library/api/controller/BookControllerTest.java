package ru.mentee.library.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.api.mapper.BookMapper;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

  @Mock BookService bookService;

  @Mock BookMapper mockedMapper;

  @InjectMocks BookController bookController;

  BookMapper mapper = Mappers.getMapper(BookMapper.class);

  @Test
  @DisplayName("Успешное создание книги")
  public void testBookCreation() {

    CreateBookRequest req =
        CreateBookRequest.builder()
            .title("Book Title")
            .author("Author")
            .isbn("978-5-17-118142-3")
            .publishedDate(LocalDate.now())
            .build();

    Book expectedBook =
        Book.builder()
            .id(1L)
            .title(req.getTitle())
            .author(req.getAuthor())
            .isbn(req.getIsbn())
            .publishedDate(req.getPublishedDate())
            .available(true)
            .build();

    when(bookService.createBook(any(Book.class))).thenReturn(expectedBook);
    when(mockedMapper.toModel(any())).thenReturn(expectedBook);

    ResponseEntity<Void> actual = bookController.createBook(req);
    assertThat(actual)
        .isEqualTo(
            ResponseEntity.created(URI.create("/api/books/" + expectedBook.getId())).build());
  }

  @Test
  @DisplayName("Получение всех книг")
  public void testGetAllBooks() {

    Book expectedBook =
        Book.builder()
            .id(1L)
            .title("title0")
            .author("author0")
            .isbn("isbn0")
            .publishedDate(LocalDate.now())
            .available(true)
            .build();
    Book expectedBook1 =
        Book.builder()
            .id(2L)
            .title("title1")
            .author("author1")
            .isbn("isbn1")
            .publishedDate(LocalDate.now())
            .available(true)
            .build();

    List<BookDto> expectedBooks =
        (Stream.of(expectedBook, expectedBook1).map(mapper::toDto).toList());

    when(bookService.getAllBooks()).thenReturn(expectedBooks);

    ResponseEntity<List<BookDto>> actual = bookController.getBooks();
    assertThat(actual).isEqualTo(ResponseEntity.ok(expectedBooks));
  }
}
