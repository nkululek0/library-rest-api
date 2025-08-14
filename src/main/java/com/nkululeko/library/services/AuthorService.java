package com.nkululeko.library.services;

import java.util.List;
import java.util.Optional;

import com.nkululeko.library.domain.entities.Author;

public interface AuthorService {

  public Author save(Long id, Author author);
  
  public List<Author> findAll();
  
  public Optional<Author> findOne(Long id);
  
  public boolean isExists(Long id);

  public Author partialUpdate(Long id, Author author);
  
  public void deleteAuthor(Long id);
}
