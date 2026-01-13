package ru.mentee.banking.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.banking.domain.model.User;
import ru.mentee.banking.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public User findByUsername(String username) {
    log.info("Finding user with username: " + username);
    return userRepository.findByUsername(username).stream()
        .peek(user -> log.info("Пользователь {} найден", username))
        .findAny()
        .orElseThrow(() -> new RuntimeException("User not found"));
  }
}
