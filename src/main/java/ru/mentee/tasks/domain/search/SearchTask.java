package ru.mentee.tasks.domain.search;

import java.util.List;
import ru.mentee.tasks.api.generated.dto.Task;

public record SearchTask(long totalCount, List<Task> tasks) {}
