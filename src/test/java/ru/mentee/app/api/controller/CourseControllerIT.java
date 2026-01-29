package ru.mentee.app.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import ru.mentee.app.BasePostgresIT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Sql(statements = """
        INSERT INTO INSTRUCTOR (id, name)
            VALUES 
                ('11111111-1111-1111-1111-111111111111', 'Sergey'),
                ('11111111-1111-1111-1111-111111111112', 'Dmitry');
        INSERT INTO COURSE (id, title, description, category, level, duration, price, instructor_id)
            VALUES ('11111111-1111-1111-2222-111111111111', 'Java с нуля', 'Стань разработчиком за неделю!', 'coding', 'BEGINNER', 40,100000.00, '11111111-1111-1111-1111-111111111111'),
             ('11111111-1111-1111-2222-111111111112', 'Java middle', 'Повысь грейд!', 'coding', 'INTERMEDIATE', 20,155000.00, '11111111-1111-1111-1111-111111111111'),
             ('11111111-1111-1111-2222-111111111113', 'Системный дизайн и архитектура', 'Повысь грейд!', 'system design', 'ADVANCED', 200,155000.00, '11111111-1111-1111-1111-111111111112');
        commit;
        """)
public class CourseControllerIT extends BasePostgresIT {
    @Test
    void whenGetPresentCoursesJsonAcceptThenOk(){
        String url = "/api/v1/courses?category={category}&level={level}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);


        var category = "coding";
        var level = "BEGINNER";
        var x = restTemplate.getForEntity(url, String.class, category, level).getBody();
        var y = restTemplate.getForEntity("/api/v1/courses", String.class).getBody();
        var z = restTemplate.exchange(url,
                HttpMethod.GET,
                requestEntity,
                String.class,
                category,
                level
                );

    }

    @Test
    void whenGetPresentCoursesXmlAcceptThenOk(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("124");
        list.add("125");
        list.add("126");
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(list::indexOf, i -> i));
        System.out.println(map);
    }
    void whenGetPresentCoursesCsvAcceptThenOk(){


    }
}
