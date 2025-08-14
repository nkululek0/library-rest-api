package com.nkululeko.library.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nkululeko.library.domain.dto.AuthorDto;
import com.nkululeko.library.domain.entities.Author;
import com.nkululeko.library.mappers.Mapper;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> {

  private ModelMapper modelMapper;
  
  public AuthorMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }
  
  @Override
  public AuthorDto mapTo(Author author) {
    return modelMapper.map(author, AuthorDto.class);
  }

  @Override
  public Author mapFrom(AuthorDto authorDto) {
    return modelMapper.map(authorDto, Author.class);
  }

}
