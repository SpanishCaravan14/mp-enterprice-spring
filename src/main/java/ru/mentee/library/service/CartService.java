package ru.mentee.library.service;

public interface CartService {
  void addBookToCart(Long bookId, Long customerId);
}
