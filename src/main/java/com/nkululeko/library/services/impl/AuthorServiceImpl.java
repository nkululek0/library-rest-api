package com.nkululeko.library.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.nkululeko.library.domain.entities.Author;
import com.nkululeko.library.repositories.AuthorRepository;
import com.nkululeko.library.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
  
  private AuthorRepository authorRepository;
  
  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public Author save (Long id, Author author) {
    if (!Objects.isNull(author)) {
      author.setId(id);
    }
    
    return authorRepository.save(author);
  }

  @Override
  public List<Author> findAll () {
    List<Author> authors = StreamSupport.stream(authorRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());

    return authors;
  }

  @Override
  public Optional<Author> findOne (Long id) {
    Optional<Author> author = authorRepository.findById(id);

    return author;
  }

  @Override
  public boolean isExists (Long id) {
    boolean result = authorRepository.existsById(id);

    return result;
  }

  @Override
  public Author partialUpdate (Long id, Author author) {
    author.setId(id);
    
    Author updatedAuthor = authorRepository.findById(id).map(existingAuthor -> {
      Optional.ofNullable(author.getName()).ifPresent(existingAuthor::setName);
      Optional.ofNullable(author.getAge()).ifPresent(existingAuthor::setAge);
      
      return authorRepository.save(existingAuthor);
    })
    .orElseThrow(() -> new RuntimeException("Author does not exist"));
    
    return updatedAuthor;
  }

  @Override
  public void deleteAuthor (Long id) {
    authorRepository.deleteById(id);
  }
}
