package ru.mentee.library.api.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.domain.model.Book;

@Slf4j
@UtilityClass
public class BookMapper {

    public static Book toBookCreateModel(CreateBookRequest createBookRequest) {
        log.info("Mapping toBookCreateModel from: " + createBookRequest);
        return Book.builder().title(createBookRequest.getTitle())
                .author(createBookRequest.getAuthor())
                .isbn(createBookRequest.getIsbn())
                .publishedDate(createBookRequest.getPublishedDate())
                .available(true)
                .build();
    }

    public static BookDto toBookReadModel(Book book) {
        log.info("Mapping toBookReadModel from book with isbn: " + book.getIsbn());
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .available(book.getAvailable())
                .build();
    }
}
