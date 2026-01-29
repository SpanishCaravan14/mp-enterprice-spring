package ru.mentee.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

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
}
