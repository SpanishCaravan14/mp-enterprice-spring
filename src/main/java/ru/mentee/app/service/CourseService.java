package ru.mentee.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.mentee.app.api.generated.dto.CourseList;
import ru.mentee.app.api.mapper.CourseMapper;
import ru.mentee.app.domain.entity.CourseEntity;
import ru.mentee.app.domain.search.SearchInfo;
import ru.mentee.app.domain.repository.CourseRepository;
import ru.mentee.app.domain.specification.CourseSpecification;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    public CourseList getCourses(SearchInfo searchInfo) {
        Specification<CourseEntity> specification = CourseSpecification.buildSpecification(searchInfo);
        var courseList = courseRepository.findAll(specification).stream().map(courseMapper::toDto).toList();
        return new CourseList().courses(courseList).totalCount(courseList.size());
    }
}
