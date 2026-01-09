package ru.mentee.library.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.service.BookService;

import java.net.URI;
import java.util.List;

import static ru.mentee.library.api.mapper.BookMapper.toBookCreateModel;

@Slf4j
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(@Qualifier("fiction") BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public ResponseEntity<List<BookDto>> getBooks() {
        log.info("Обработка вызова по поиску всех книг");
        var books = bookService.getAllBooks();
        log.info("Найдено {} книг", books.size());
        return ResponseEntity.ok(books);
    }

    @PostMapping("/api/books")
    public ResponseEntity<Void> createBook(@RequestBody @Valid CreateBookRequest createBookRequest) {
        log.info("Обработка вызова по созданию книги с входящими данными: {}", createBookRequest);
        var book = toBookCreateModel(createBookRequest);
        var createdBook = bookService.createBook(book);
        log.info("Успешно создана книга с isbn: {}", createdBook.getIsbn());
        return ResponseEntity.created(URI.create("/api/books/" + createdBook.getId())).build();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable @NotNull @Min(1) Long id) {
        log.info("Обработка вызова по получению книги с ID: {}", id);
        var book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
}
