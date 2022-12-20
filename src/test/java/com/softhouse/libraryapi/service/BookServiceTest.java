package com.softhouse.libraryapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.softhouse.libraryapi.exception.BusinessException;
import com.softhouse.libraryapi.model.entity.Book;
import com.softhouse.libraryapi.model.repository.BookRepository;
import com.softhouse.libraryapi.service.impl.BookServiceImpl;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

	BookService service;
	
	@MockBean
	BookRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImpl(repository);
	}
	
	@Test
	@DisplayName("Deve salvar um livro")
	public void saveBookTest() {
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		
		
		Book savedBook = service.save(book);
		
		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getIsbn()).isEqualTo("1408");
		assertThat(savedBook.getTitle()).isEqualTo("Ilíada");
		assertThat(savedBook.getAuthor()).isEqualTo("Homero");
	}
	
	@Test
	@DisplayName("Deve lançar erro de negócio, ao tentar salvar livro com isbn duplicado.")
	public void sholdNotSaveABookWithDuplicatedISBN() {
		Book book = createValidBook();
		
		Throwable exception = Assertions.catchThrowable(() -> service.save(book));
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Isbn já cadastrado!");
		
		Mockito.verify(repository, Mockito.never()).save(book);
	}

	private Book createValidBook() {
		return new Book((long)21,"Homero", "Ilíada", "1408");
	}
}
