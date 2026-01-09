package ru.mentee.library.service.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IsbnValidatorTest {
    @Test
    void shouldReturnTrueForValidIsbn10() {
        assertThat(IsbnValidator.validateIsbn("0-306-40615-2"))
                .isTrue();
    }

    @Test
    void shouldReturnTrueForValidIsbn10WithoutDashes() {
        assertThat(IsbnValidator.validateIsbn("0306406152"))
                .isTrue();
    }

    @Test
    void shouldReturnTrueForValidIsbn13() {
        assertThat(IsbnValidator.validateIsbn("978-0-306-40615-7"))
                .isTrue();
    }

    @Test
    void shouldReturnTrueForValidIsbn13WithoutDashes() {
        assertThat(IsbnValidator.validateIsbn("9780306406157"))
                .isTrue();
    }

    @Test
    void shouldReturnFalseForNull() {
        assertThat(IsbnValidator.validateIsbn(null))
                .isFalse();
    }

    @Test
    void shouldReturnFalseForIsbnWithLetters() {
        assertThat(IsbnValidator.validateIsbn("ISBN0306406152"))
                .isFalse();
    }

    @Test
    void shouldReturnFalseForIsbnWithExtraSymbols() {
        assertThat(IsbnValidator.validateIsbn("0306-40615-2#"))
                .isFalse();
    }
}
