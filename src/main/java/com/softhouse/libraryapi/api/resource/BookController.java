package com.softhouse.libraryapi.api.resource;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softhouse.libraryapi.api.dto.BookDTO;
import com.softhouse.libraryapi.api.exception.ApiErrors;
import com.softhouse.libraryapi.exception.BusinessException;
import com.softhouse.libraryapi.model.entity.Book;
import com.softhouse.libraryapi.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	
	private BookService service;
	private ModelMapper modelMapper;
	
	public BookController(BookService service, ModelMapper modelMapper) {
		this.service = service;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		Book entity = modelMapper.map(dto, Book.class);
		
		BookDTO book = modelMapper.map(entity, BookDTO.class);
		return book;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		ApiErrors err = new ApiErrors(bindingResult);
		return err;
	}
	
	@ExceptionHandler(com.softhouse.libraryapi.exception.BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException ex) {
		ApiErrors err = new ApiErrors(ex);
		return err;
	}
}
