package com.softhouse.libraryapi.api.dto;

import jakarta.validation.constraints.NotEmpty;


public class BookDTO {
	
	private Long id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String author;
	@NotEmpty
	private String isbn;
	
	public BookDTO() {
	}
	
	public BookDTO(String author, String title, String isbn) {
		super();
		this.author = author;
		this.title = title;
		this.isbn = isbn;
	}
	
	public BookDTO(Long id, String author, String title, String isbn) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
}
