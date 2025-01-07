package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.mappers.Mapper;
import com.devtiro.database.service.impl.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    Mapper<BookEntity, BookDto> bookMapper;
    public BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PostMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book){
        BookEntity bookEntity=bookMapper.mapFrom(book);
        BookEntity savedBookEntity=bookService.createBook(bookEntity.getIsbn(),bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);

    }

    @GetMapping(path = "/books")
    public List<BookDto> listBook()
    {
       List<BookEntity> books=bookService.findAll();
      return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path="/books/{isbn}")
    public  ResponseEntity<BookDto> getBookByIsbn(@PathVariable("isbn") String isbn)
    {
        Optional<BookEntity> foundBook=bookService.findById(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
