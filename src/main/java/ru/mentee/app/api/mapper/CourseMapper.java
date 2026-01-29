package ru.mentee.app.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mentee.app.api.generated.dto.Course;
import ru.mentee.app.domain.search.SearchInfo;
import ru.mentee.app.domain.entity.CourseEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CourseMapper {
    Course toDto(CourseEntity courseEntity);
    @Mapping(target = "filter", expression = "java(toFilter(category, level))")
    SearchInfo toSearchInfo(String category, String level);
    SearchInfo.Filter toFilter(String category, String level);
}
