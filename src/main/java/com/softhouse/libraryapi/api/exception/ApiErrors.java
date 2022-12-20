package com.softhouse.libraryapi.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

public class ApiErrors {
	
	private List<String> errors;
	
	public ApiErrors(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach( erro -> this.errors.add(erro.getDefaultMessage()));
	}

	public ApiErrors(com.softhouse.libraryapi.exception.BusinessException ex) {
		this.errors = Arrays.asList(ex.getMessage());
	}
	
}
