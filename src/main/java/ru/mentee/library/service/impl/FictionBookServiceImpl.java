package ru.mentee.library.service.impl;

import static ru.mentee.library.service.validation.IsbnValidator.validateIsbn;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.mapper.BookMapper;
import ru.mentee.library.domain.exception.InvalidIsbnException;
import ru.mentee.library.domain.exception.NotFoundBookException;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.repository.BookRepository;
import ru.mentee.library.service.BookService;

@Service
@Primary
@Qualifier("fiction")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FictionBookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  @Override
  public Book createBook(Book book) {
    log.info("Creating fiction book with isbn: {}", book.getIsbn());
    if (validateIsbn(book.getIsbn())) {
      return bookRepository.save(book);
    } else {
      log.error("Invalid isbn: {}", book.getIsbn());
      throw new InvalidIsbnException();
    }
  }

  @Override
  public List<BookDto> getAllBooks() {
    log.info("Trying to get all fiction books");
    return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public BookDto getBookById(Long id) {
    log.info("Get fiction book by id: {}", id);
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Book with id {} not found", id);
                  return new NotFoundBookException();
                });
    return bookMapper.toDto(book);
  }

  @PostConstruct
  public void init() {
    log.info("Init Fiction Book Service");
  }

  @PreDestroy
  public void destroy() {
    log.info("Destroy Fiction Book Service");
  }
}
