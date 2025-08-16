package com.nkululeko.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nkululeko.library.domain.entities.Book;

public interface BookService {

  public Book createUpdateBook(String isbn, Book book);
  
  public List<Book> findAll();
  
  public Page<Book> findAll(Pageable pageable);
  
  public Optional<Book> findOne(String isbn);
  
  public boolean isExists(String isbn);
  
  public Book partialUpdateBook(String isbn, Book book);
  
  public void deleteBook(String isbn);
}
