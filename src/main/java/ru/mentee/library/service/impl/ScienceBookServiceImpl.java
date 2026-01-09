package ru.mentee.library.service.impl;

import static ru.mentee.library.api.mapper.BookMapper.toBookReadModel;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.mapper.BookMapper;
import ru.mentee.library.domain.exception.NotFoundBookException;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.repository.BookRepository;
import ru.mentee.library.service.BookService;

@Service
@Qualifier("science")
@Transactional
@Slf4j
public class ScienceBookServiceImpl implements BookService {

  @Autowired private BookRepository bookRepository;

  @Override
  public Book createBook(Book book) {
    log.info("Create science book: {}", book.getIsbn());
    return bookRepository.save(book);
  }

  @Override
  public List<BookDto> getAllBooks() {
    log.info("Get science books");
    return bookRepository.findAll().stream()
        .map(BookMapper::toBookReadModel)
        .collect(Collectors.toList());
  }

  @Override
  public BookDto getBookById(Long id) {
    log.info("Get science book by id: {}", id);
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Book with id {} not found", id);
                  return new NotFoundBookException();
                });
    return toBookReadModel(book);
  }

  @PostConstruct
  public void init() {
    log.info("Initializing Science Book Service");
  }

  @PreDestroy
  public void destroy() {
    log.info("Destroying Science Book Service");
  }
}
