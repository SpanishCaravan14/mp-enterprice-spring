package ru.mentee.library.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.mentee.library.service.CartService;

@Scope("prototype")
@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Override
    public void addBookToCart(Long bookId, Long customerId) {
        //todo
    }
}
