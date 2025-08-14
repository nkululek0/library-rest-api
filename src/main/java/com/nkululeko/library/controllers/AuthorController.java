package com.nkululeko.library.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nkululeko.library.domain.dto.AuthorDto;
import com.nkululeko.library.domain.entities.Author;
import com.nkululeko.library.mappers.Mapper;
import com.nkululeko.library.services.AuthorService;

@RestController
public class AuthorController {
  
  private AuthorService authorService;
  
  private Mapper<Author, AuthorDto> authorMapper;
  
  public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
  }
  
  @PostMapping(path = "/authors")
  public ResponseEntity<AuthorDto> createAuthor (@RequestBody AuthorDto authorDto) {
    Author author = authorMapper.mapFrom(authorDto);
    Author savedAuthor = authorService.save(null, author);

    return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
  }
  
  @GetMapping(path = "/authors")
  public ResponseEntity<List<AuthorDto>> listAuthors () {
    List<Author> authors = authorService.findAll();
    List<AuthorDto> foundAuthors = authors.stream()
        .map(authorMapper::mapTo)
        .collect(Collectors.toList());
    
    return new ResponseEntity<>(foundAuthors, HttpStatus.OK);
  }
  
  @GetMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> getAuthor (@PathVariable Long id) {
    Optional<Author> author = authorService.findOne(id);

    return author.map(possibleAuthor -> {
      AuthorDto authorDto = authorMapper.mapTo(possibleAuthor);
      
      return new ResponseEntity<>(authorDto, HttpStatus.OK);
    })
    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  @PutMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> fullUpdateAuthor (@PathVariable Long id, @RequestBody AuthorDto authorDto) {
    if (!authorService.isExists(id)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Author author = authorMapper.mapFrom(authorDto);
    Author updatedAuthor = authorService.save(id, author);
    
    return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
  }
  
  @PatchMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> partialUpdateAuthor (@PathVariable Long id, @RequestBody AuthorDto authorDto) {
    if (!authorService.isExists(id)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    Author author = authorMapper.mapFrom(authorDto);
    Author updatedAuthor = authorService.partialUpdate(id, author);

    return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
  }
  
  @DeleteMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> deleteAuthor (@PathVariable Long id) {
    if (!authorService.isExists(id)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    authorService.deleteAuthor(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
