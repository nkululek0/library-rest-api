package com.nkululeko.library.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nkululeko.library.domain.dto.BookDto;
import com.nkululeko.library.domain.entities.Book;
import com.nkululeko.library.mappers.Mapper;
import com.nkululeko.library.services.BookService;

@RestController
public class BookController {
  
  private BookService bookService;
  
  private Mapper<Book, BookDto> bookMapper;
  
  public BookController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
    this.bookService = bookService;
    this.bookMapper = bookMapper;
  }

  @PutMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> createUpdateBook (@PathVariable String isbn, @RequestBody BookDto bookDto) {
    boolean bookExists = bookService.isExists(isbn);
    Book book = bookMapper.mapFrom(bookDto);
    Book savedBook = bookService.createUpdateBook(isbn, book);
    BookDto response = bookMapper.mapTo(savedBook);
    
    if (bookExists) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
  
  @GetMapping(path = "/books")
  public ResponseEntity<List<BookDto>> findAll () {
    List<Book> books = bookService.findAll();
    List<BookDto> response = books.stream().map(bookMapper::mapTo).toList();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @GetMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> findOne (@PathVariable String isbn) {
    Optional<Book> book = bookService.findOne(isbn);
    
    return book.map(possibleBook -> {
      BookDto bookDto = bookMapper.mapTo(possibleBook);
      
      return new ResponseEntity<>(bookDto, HttpStatus.OK);
    })
    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  @PatchMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> partialUpdateBook (@PathVariable String isbn, @RequestBody BookDto bookDto) {
    if (!bookService.isExists(isbn)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    Book book = bookMapper.mapFrom(bookDto);
    Book updatedBook = bookService.partialUpdateBook(isbn, book);
    
    return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
  }
  
  @DeleteMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> deleteBook (@PathVariable String isbn) {
    if (!bookService.isExists(isbn)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    bookService.deleteBook(isbn);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
