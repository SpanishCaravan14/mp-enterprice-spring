package ru.mentee.app.domain.search;

import lombok.Builder;

public record SearchInfo (Filter filter) {
    @Builder
    public record Filter(String category, String level) {}
}
