package com.softhouse.libraryapi.model.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.softhouse.libraryapi.model.entity.Book;

import jakarta.persistence.EntityManager;

public class BookRepository extends SimpleJpaRepository<Book, Long>{

	public BookRepository(Class<Book> domainClass, EntityManager em) {
		super(domainClass, em);
	}

	public boolean existsByIsbn(String isbn) {
		return false;
	}
	
}
