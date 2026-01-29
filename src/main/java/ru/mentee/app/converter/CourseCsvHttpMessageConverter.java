package ru.mentee.app.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import ru.mentee.app.api.generated.dto.Course;
import ru.mentee.app.api.generated.dto.CourseList;
import ru.mentee.app.api.generated.dto.Instructor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class CourseCsvHttpMessageConverter extends AbstractHttpMessageConverter <CourseList> {
    public CourseCsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return CourseList.class.isAssignableFrom(clazz);
    }

    @Override
    protected CourseList readInternal(Class<? extends CourseList> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new HttpMessageNotReadableException("CsvHttpMessageConverter does not support reading input");
    }

    @Override
    protected void writeInternal(CourseList courseList, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        outputMessage.getHeaders().add(
                "Content-Disposition",
                "attachment; filename=\"courses.csv\""
        );

        outputMessage.getBody().write(0xEF);
        outputMessage.getBody().write(0xBB);
        outputMessage.getBody().write(0xBF);

        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(outputMessage.getBody(), StandardCharsets.UTF_8))) {

            writer.println("id,title,description,category,level,duration,price,instructor_id,instructor_name");

            List<Course> courses = courseList.getCourses().isEmpty()
                    ? Collections.emptyList()
                    : courseList.getCourses();

            for (Course course : courses) {
                String row = String.join(",",
                        escapeCsvValue(course.getId()),
                        escapeCsvValue(course.getTitle()),
                        escapeCsvValue(course.getDescription()),
                        escapeCsvValue(course.getCategory()),
                        escapeCsvValue(getLevelValue(course.getLevel())),
                        escapeCsvValue(getDurationValue(course.getDuration())),
                        escapeCsvValue(getPriceValue(course.getPrice())),
                        escapeCsvValue(getInstructorId(course.getInstructor())),
                        escapeCsvValue(getInstructorName(course.getInstructor()))
                );
                writer.println(row);
            }
        }
    }

    // Метод для экранирования CSV значений
    private String escapeCsvValue(String value) {
        if (value == null || value.isBlank()) {
            return ""; // Пустая строка для null/blank
        }

        // Если есть запятые, кавычки или переносы строк - экранируем
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }

    // Вспомогательные методы для безопасного получения значений
    private String getLevelValue(Course.LevelEnum level) {
        return level != null && level.getValue() != null
                ? level.getValue()
                : "";
    }

    private String getDurationValue(Integer duration) {
        return duration != null
                ? duration.toString()
                : "";
    }

    private String getPriceValue(BigDecimal price) {
        return price != null
                ? price.toPlainString()
                : "";
    }

    private String getInstructorId(Instructor instructor) {
        return instructor != null && instructor.getId() != null
                ? instructor.getId()
                : "";
    }

    private String getInstructorName(Instructor instructor) {
        return instructor != null && instructor.getName() != null
                ? instructor.getName()
                : "";
    }
}
