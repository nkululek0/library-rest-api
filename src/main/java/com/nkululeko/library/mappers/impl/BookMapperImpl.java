package com.nkululeko.library.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nkululeko.library.domain.dto.BookDto;
import com.nkululeko.library.domain.entities.Book;
import com.nkululeko.library.mappers.Mapper;

@Component
public class BookMapperImpl implements Mapper<Book, BookDto> {
  
  private ModelMapper modelMapper;
  
  public BookMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public BookDto mapTo (Book book) {
    return modelMapper.map(book, BookDto.class);
  }

  @Override
  public Book mapFrom(BookDto bookDto) {
    return modelMapper.map(bookDto, Book.class);
  }

}
