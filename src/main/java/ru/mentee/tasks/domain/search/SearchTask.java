package ru.mentee.tasks.domain.search;

import ru.mentee.api.generated.dto.Task;

import java.util.List;

public record SearchTask(long totalCount, List<Task> tasks) {}
