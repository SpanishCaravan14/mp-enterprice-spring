package ru.mentee.library.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.domain.exception.InvalidIsbnException;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static ru.mentee.library.api.mapper.BookMapper.toBookReadModel;

@ExtendWith(MockitoExtension.class)
public class FictionBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    FictionBookServiceImpl fictionBookService;

    @Test
    @DisplayName("Успешное создание книги")
    public void testBookCreation() {
        Book expectedBook = new Book();
        expectedBook.setTitle("Book Title");
        expectedBook.setAuthor("Author");
        expectedBook.setIsbn("978-5-17-118142-3");
        expectedBook.setPublishedDate(LocalDate.now());
        expectedBook.setAvailable(true);

        when(bookRepository.save(any(Book.class)))
                .thenAnswer(invocation -> {
                    Book saved = invocation.getArgument(0);
                    saved.setId(1L);
                    return saved;
                });

        Book actualBook = fictionBookService.createBook(expectedBook);

        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("title", "Book Title")
                .hasFieldOrPropertyWithValue("author", "Author")
                .hasFieldOrPropertyWithValue("isbn", "978-5-17-118142-3")
                .hasFieldOrPropertyWithValue("publishedDate", LocalDate.now())
                .hasFieldOrPropertyWithValue("available", true)
                .hasFieldOrPropertyWithValue("id", 1L);

        verify(bookRepository, times(1)).save(expectedBook);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Попытка созздания книги с некорректным isbn")
    public void testBookCreationFailure_whenIsbnInvalid() {
        Book invalidBook = new Book();
        invalidBook.setTitle("Book Title");
        invalidBook.setAuthor("Author");
        invalidBook.setIsbn("isbn");
        invalidBook.setPublishedDate(LocalDate.now());

        assertThatThrownBy(() -> fictionBookService.createBook(invalidBook))
                .isInstanceOf(InvalidIsbnException.class)
                .hasMessageContaining("Некорректный ISBN");
    }

    @Test
    @DisplayName("Успешное получение всех книг")
    public void testGetAllBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setIsbn("978-5-17-118142-0");
        book.setPublishedDate(LocalDate.now());
        book.setAvailable(true);

        Book book1 = new Book();
        book.setId(2L);
        book1.setTitle("Book Title 1");
        book1.setAuthor("Author 1");
        book1.setIsbn("978-5-17-118142-1");
        book1.setPublishedDate(LocalDate.now());
        book1.setAvailable(false);

        Book book2 = new Book();
        book.setId(3L);
        book2.setTitle("Book Title 2");
        book2.setAuthor("Author 2");
        book2.setIsbn("978-5-17-118142-2");
        book2.setPublishedDate(null);
        book2.setAvailable(true);

        when(bookRepository.findAll()).thenReturn(List.of(book, book1, book2));

        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .available(book.getAvailable())
                .build();

        BookDto bookDto1 = BookDto.builder()
                .id(book1.getId())
                .title(book1.getTitle())
                .author(book1.getAuthor())
                .isbn(book1.getIsbn())
                .publishedDate(book1.getPublishedDate())
                .available(book1.getAvailable())
                .build();

        BookDto bookDto2 = BookDto.builder()
                .id(book2.getId())
                .title(book2.getTitle())
                .author(book2.getAuthor())
                .isbn(book2.getIsbn())
                .publishedDate(book2.getPublishedDate())
                .available(book2.getAvailable())
                .build();

        var actualBooks = fictionBookService.getAllBooks();

        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);

        assertThat(actualBooks)
                .hasSize(3)
                .containsExactly(bookDto, bookDto1, bookDto2);
    }

    @Test
    @DisplayName("Успешное получение книги по id")
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setIsbn("978-5-17-118142-0");
        book.setPublishedDate(LocalDate.now());
        book.setAvailable(true);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto expectedBook = toBookReadModel(book);
        BookDto actualBook = fictionBookService.getBookById(1L);

        assertThat(actualBook).isEqualTo(expectedBook);
    }
}
