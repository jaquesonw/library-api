package com.softhouse.libraryapi.model.entity;

import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;

public class Book {
	
	private Long id;
	@NotEmpty
	private String author;
	@NotEmpty
	private String title;
	@NotEmpty
	private String isbn;
	
	public Book() {
	}

	public Book(Long id, String author, String title, String isbn) {
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
