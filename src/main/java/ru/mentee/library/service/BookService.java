package ru.mentee.library.service;

import java.util.List;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.domain.model.Book;

public interface BookService {
  Book createBook(Book book);

  BookDto getBookById(Long id);

  List<BookDto> getAllBooks();
}
