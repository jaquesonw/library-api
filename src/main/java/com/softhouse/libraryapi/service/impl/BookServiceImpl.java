package com.softhouse.libraryapi.service.impl;

import org.springframework.stereotype.Service;

import com.softhouse.libraryapi.exception.BusinessException;
import com.softhouse.libraryapi.model.entity.Book;
import com.softhouse.libraryapi.model.repository.BookRepository;
import com.softhouse.libraryapi.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	private BookRepository repository; 
	
	
	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}


	@Override
	public Book save(Book book) {
		if (repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn já cadastrado!");
		}
		Book b1 = repository.save(book);
		return b1;
	}

}
