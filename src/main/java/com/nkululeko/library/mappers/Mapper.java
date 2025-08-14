package com.nkululeko.library.mappers;

public interface Mapper<A, B> {

  B mapTo(A a);
  
  A mapFrom(B b);
}
