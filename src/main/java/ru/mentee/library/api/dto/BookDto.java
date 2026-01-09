package ru.mentee.library.api.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
  private Long id;
  private String title;
  private String author;
  private String isbn;
  private LocalDate publishedDate;
  private Boolean available;
}
