package ru.mentee.library.service;

import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.domain.model.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    BookDto getBookById(Long id);
    List<BookDto> getAllBooks();
}
