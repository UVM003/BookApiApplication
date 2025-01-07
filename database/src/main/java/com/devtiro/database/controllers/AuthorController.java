package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.mappers.Mapper;
import com.devtiro.database.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    Mapper<AuthorEntity,AuthorDto> authorMapper;
    private AuthorService authorService;

    public AuthorController(AuthorService authorService,Mapper<AuthorEntity,AuthorDto> authorMapper)
    {
        this.authorService=authorService;
        this.authorMapper=authorMapper;
    }
    @PostMapping(path="/authors")
    public ResponseEntity<AuthorDto> creteAuthor(@RequestBody AuthorDto author){
 AuthorEntity authorEntity=authorMapper.mapFrom(author);
 AuthorEntity savedAuthorEntity=authorService.createAuthor(authorEntity);
 return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED) ;
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors=authorService.findAll();
        return  authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findById(id);
       return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,@RequestBody AuthorDto authorDto)
    {
        if(!authorService.isExists(id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
       AuthorEntity authorEntity =authorMapper.mapFrom(authorDto);
       AuthorEntity savedAuthorEntity=authorService.createAuthor(authorEntity);
       return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity),HttpStatus.OK);


    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id") Long id,@RequestBody AuthorDto authorDto)
    {
        if(!authorService.isExists(id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorEntity =authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor=authorService.partialUpdate(id,authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor),HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public  ResponseEntity<AuthorDto> delete(@PathVariable Long id)
    {
        if(!authorService.isExists(id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}