package ru.mentee.tasks.domain.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.mentee.tasks.domain.model.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
  Page<TaskEntity> findAll(@Nullable Specification<TaskEntity> spec, Pageable pageable);
}
