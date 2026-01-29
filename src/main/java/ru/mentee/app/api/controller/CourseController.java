package ru.mentee.app.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.app.api.generated.controller.DefaultApi;
import ru.mentee.app.api.generated.dto.CourseList;
import ru.mentee.app.api.mapper.CourseMapper;
import ru.mentee.app.service.CourseService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseController implements DefaultApi {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    @Override
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            "text/csv"
    })
    public ResponseEntity<CourseList> apiV1CoursesGet(String accept, String category, String level) {
        CourseList courseList = courseService.getCourses(courseMapper.toSearchInfo(category, level));
        return ResponseEntity.ok(courseList);
    }

}
