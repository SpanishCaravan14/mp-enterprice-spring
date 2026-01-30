package ru.mentee.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "course")
public class CourseEntity {
    @Id
    private String id;

    private String title;

    private String description;

    private String category;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    private Integer duration;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private InstructorEntity instructor;

    @ManyToMany
    @JoinTable(
            name = "user_course_mapping",
            joinColumns = @JoinColumn(              // колонка для текущей сущности (User)
                    name = "course_id",                   // имя колонки в связной таблице
                    referencedColumnName = "id"         // имя колонки в таблице User
            ),
            inverseJoinColumns = @JoinColumn(       // колонка для связанной сущности (Course)
                    name = "user_id",                 // имя колонки в связной таблице
                    referencedColumnName = "id"         // имя колонки в таблице Course
            )
    )
    private List<UserEntity> users;
}
