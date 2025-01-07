package com.devtiro.database.service.impl;

import com.devtiro.database.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity createBook(String isbn,BookEntity bookEntity);


    List<BookEntity> findAll();

    Optional<BookEntity> findById(String isbn);
}
