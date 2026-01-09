package ru.mentee.library.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.stream.Collectors;

import static ru.mentee.library.api.mapper.BookMapper.toBookReadModel;
import static ru.mentee.library.service.validation.IsbnValidator.validateIsbn;

@Slf4j
@Service
@Primary
@Qualifier("fiction")
@Transactional
public class FictionBookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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
        return bookRepository.findAll().stream().map(BookMapper::toBookReadModel).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Get fiction book by id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book with id {} not found", id);
                    return new NotFoundBookException();
                });
        return toBookReadModel(book);
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
