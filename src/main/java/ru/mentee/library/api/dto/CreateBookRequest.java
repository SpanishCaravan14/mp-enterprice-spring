package ru.mentee.library.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBookRequest {

  @NotNull
  @Size(min = 1, max = 200)
  private String title;

  @NotNull
  @Size(min = 1, max = 100)
  private String author;

  @NotNull
  @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
  private String isbn;

  private LocalDate publishedDate;
}
