package com.br.booksManagement.controller;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.booksManagement.model.Books;
import com.br.booksManagement.repository.BookRepository;
import com.br.booksManagement.service.BookService;
import com.br.booksManagement.util.UtilTest;

@ExtendWith(SpringExtension.class)
public class BooksControllerTest {

	@Mock
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookController bookController;

	@BeforeEach
	void setUp() {

		BDDMockito.when(bookService.getAll()).thenReturn(List.of(UtilTest.createBook()));

		BDDMockito.when(bookService.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(UtilTest.createBook()));

		BDDMockito.when(bookService.findByTitle(ArgumentMatchers.anyString())).thenReturn(UtilTest.createBook());

		BDDMockito.when(bookService.save(ArgumentMatchers.any(Books.class))).thenReturn(UtilTest.createBook());

		BDDMockito.when(bookRepository.save(ArgumentMatchers.any(Books.class))).thenReturn(UtilTest.createBook());

		BDDMockito.when(bookService.save(ArgumentMatchers.any(Books.class))).thenReturn(UtilTest.createBook());

		BDDMockito.doNothing().when(bookService).deleteById(ArgumentMatchers.anyInt());
	}

	@Test
	@DisplayName("expectedTitle() => Returns list of books when successful")
	void getAllTest() {
		String expectedTitle = UtilTest.createBook().getTitle();

		List<Books> books = bookController.getAll().getBody();

		Assertions.assertThat(books).isNotNull().isNotEmpty();

		Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
	}

	@Test
	@DisplayName("findByIdTest() => Find by id when Successful")
	void findByIdTest() {
		Integer expectedId = UtilTest.createBook().getId();

		Optional<Books> bookSaved = this.bookController.findByID(1).getBody();

		Assertions.assertThat(bookSaved).isNotNull();

		Assertions.assertThat(bookSaved.get().getId()).isNotNull().isEqualTo(expectedId);

	}

	@Test
	@DisplayName("findByTitleTest() => Find by title when Successful")
	void findByTitleTest() {
		String expectedTitle = UtilTest.createBook().getTitle();

		Books books = this.bookController.findByTitle("Test").getBody();

		Assertions.assertThat(books).isNotNull();

		Assertions.assertThat(books.getTitle()).isEqualTo(expectedTitle);

	}

	@Test
	@DisplayName("findByTitleEmptyTest() > Return empty when book not found")
	void findByTitleEmptyTest() {

		Books books = this.bookController.findByTitle("asasad").getBody();

		Assertions.assertThat(books).isNotNull();

	}

	@Test
	@DisplayName("save() => return book when successful")
	void save() {

		Books book = bookController.save(UtilTest.createBook(), true).getBody();

		Assertions.assertThat(book.getTitle()).isNotNull().isEqualTo(UtilTest.createBook().getTitle());

	}

	@Test
	@DisplayName("delteTest() => Returns book when successful")
	void delteTest() {

		Assertions.assertThatCode(() -> bookController.deleteByID(1)).doesNotThrowAnyException();

		ResponseEntity<Optional<Books>> book = bookController.deleteByID(1);

		Assertions.assertThat(book).isNotNull();

		Assertions.assertThat(book.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	@DisplayName("updateTest() => updates book when successful")
	void updateTest() {

		Assertions.assertThatCode(() -> bookController.update(UtilTest.createBook().getId(), UtilTest.updateBook()))
				.doesNotThrowAnyException();

		ResponseEntity<Books> entity = bookController.update(UtilTest.createBook().getId(), UtilTest.updateBook());

		Assertions.assertThat(entity).isNotNull();

		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
