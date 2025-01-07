package com.devtiro.database.service.impl;

import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport
                .stream(
                        bookRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public BookEntity createBook(String isbn,BookEntity bookEntity) {
       bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public Optional<BookEntity> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean exists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity book) {
        book.setIsbn(isbn);

        return  bookRepository.findById(isbn).map(existing -> {
                Optional.ofNullable(book.getIsbn()).ifPresent(existing::setIsbn);
            Optional.ofNullable(book.getTitle()).ifPresent(existing::setTitle);
           return bookRepository.save(existing);
    }).orElseThrow(()-> new RuntimeException("Book Does Not Exist"));
    }
}
