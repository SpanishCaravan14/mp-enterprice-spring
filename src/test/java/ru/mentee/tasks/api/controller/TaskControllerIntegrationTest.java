package ru.mentee.tasks.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.mentee.tasks.BaseIntegrationTest;
import ru.mentee.tasks.api.generated.dto.Task;

public class TaskControllerIntegrationTest extends BaseIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;

  void shouldCreateTaskWhereValidRequest(){
 //   Task response = restTemplate.postForEntity("api/v1/tasks", );
  }
}
