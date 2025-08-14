package com.nkululeko.library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nkululeko.library.domain.entities.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
