package com.nkululeko.library.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nkululeko.library.domain.entities.Book;
import com.nkululeko.library.repositories.BookRepository;
import com.nkululeko.library.services.BookService;

@Service
public class BookServiceImpl implements BookService {
  
  private BookRepository bookRepository;
  
  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book createUpdateBook (String isbn, Book book) {
    book.setIsbn(isbn);
    return bookRepository.save(book);
  }

  @Override
  public List<Book> findAll () {
    Iterable<Book> books = bookRepository.findAll();

    return StreamSupport.stream(books.spliterator(), false)
        .collect(Collectors.toList());
  }
  
  @Override
  public Page<Book> findAll (Pageable pageable) {
    Page<Book> books = bookRepository.findAll(pageable);
    
    return books;
  }

  @Override
  public Optional<Book> findOne (String isbn) {
    Optional<Book> book = bookRepository.findById(isbn);

    return book;
  }

  @Override
  public boolean isExists (String isbn) {
    return bookRepository.existsById(isbn);
  }

  @Override
  public Book partialUpdateBook (String isbn, Book book) {
    book.setIsbn(isbn);
    
    Book updatedBook = bookRepository.findById(isbn).map(existingBook -> {
      Optional.ofNullable(book.getName()).ifPresent(existingBook::setName);
      
      return bookRepository.save(existingBook);
    })
    .orElseThrow(() -> new RuntimeException("Book does not exist"));
    
    return updatedBook;
  }

  @Override
  public void deleteBook (String isbn) {
    bookRepository.deleteById(isbn);
  }
}
