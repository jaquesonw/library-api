package com.softhouse.libraryapi.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softhouse.libraryapi.api.dto.BookDTO;
import com.softhouse.libraryapi.exception.BusinessException;
import com.softhouse.libraryapi.model.entity.Book;
import com.softhouse.libraryapi.service.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

	static String BOOK_API = "/api/books";

	@Autowired
	MockMvc mvc;

	@MockBean
	BookService service;

	@Test
	@DisplayName("Deve criar livro com sucesso!.")
	public void createBookTest() throws Exception {

		BookDTO dto = createNewBookDTO();

		Book savedBook = new Book((long) 101, "J.R.R Tolkien", "O Hobbit", "18598785");

		BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

		String json = new ObjectMapper().writeValueAsString(dto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(jsonPath("id").isNotEmpty())
				.andExpect(jsonPath("title").value(dto.getTitle()))
				.andExpect(jsonPath("author").value(dto.getAuthor()))
				.andExpect(jsonPath("isbn").value(dto.getIsbn()));

	}

	@Test
	@DisplayName("Deve lançar erro de validação, quando não houverem dados suficientes para criação do livro!")
	public void createInvalidBookTest() throws Exception {

		String json = new ObjectMapper().writeValueAsString(new BookDTO());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("error", Matchers.hasSize(3)));
	}

	@Test
	@DisplayName("Deve lançar erro ao tentar cadastrar livro com isbn já utilizado")
	public void createBookWithDuplicatedIsbn() throws Exception {

		String errormsg = "Isbn já cadastrado!";

		Book book = createNewBook();

		String json = new ObjectMapper().writeValueAsString(book);

		BDDMockito.given(service.save(Mockito.any(Book.class))).willThrow(new BusinessException(errormsg));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc.perform(request)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("errors", Matchers.hasSize(1)))
				.andExpect(jsonPath("errors[0]").value(errormsg));

	}

	private Book createNewBook() {
		return new Book((long) 101, "J.R.R Tolkien", "O Hobbit", "18598785");
	}
	
	private BookDTO createNewBookDTO() {
		return new BookDTO((long) 101, "J.R.R Tolkien", "O Hobbit", "18598785");
	}
}
