package com.nkululeko.library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nkululeko.library.domain.entities.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String>,
  PagingAndSortingRepository<Book, String> {
}
