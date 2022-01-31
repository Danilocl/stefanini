package com.br.booksManagement.service;

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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.booksManagement.model.Books;
import com.br.booksManagement.repository.BookRepository;
import com.br.booksManagement.util.UtilTest;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;

	@BeforeEach
	void setUp() {
		PageImpl<Books> bookPage = new PageImpl<>(List.of(UtilTest.createBook()));

		BDDMockito.when(bookRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);

		BDDMockito.when(bookRepository.findAll()).thenReturn(List.of(UtilTest.createBook()));

		BDDMockito.when(bookRepository.findById(ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(UtilTest.createBook()));

		BDDMockito.when(bookRepository.findByTitle(ArgumentMatchers.anyString())).thenReturn(UtilTest.createBook());

		BDDMockito.when(bookRepository.save(ArgumentMatchers.any(Books.class))).thenReturn(UtilTest.createBook());

		BDDMockito.doNothing().when(bookRepository).delete(ArgumentMatchers.any(Books.class));
	}

	@Test
	@DisplayName("listAllTest() => Returns list of books when successful")
	void listAllTest() {
		String expectedTitle = UtilTest.createBook().getTitle();

		List<Books> books = bookService.getAll();

		Assertions.assertThat(books).isNotNull().isNotEmpty();

		Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
	}

	@Test
	@DisplayName("findByIdTest() => Returns books by id when successful")
	void findByIdTest() {

		List<Books> booksList = bookService.getAll();

		Optional<Books> books = bookService.findById(booksList.get(0).getId());

		Assertions.assertThat(books).isNotNull();

		Assertions.assertThat(books.get().getId()).isNotNull().isEqualTo(booksList.get(0).getId());
	}

	@Test
	@DisplayName("findByTitleTest() => Returns book by id when successful")
	void findByTitleTest() {

		List<Books> booksList = bookService.getAll();

		Books books = bookService.findByTitle(booksList.get(0).getTitle());

		Assertions.assertThat(books).isNotNull();

		Assertions.assertThat(books.getTitle()).isNotNull().isEqualTo(booksList.get(0).getTitle());
	}

	@Test
	@DisplayName("saveTest() => Save books when successful")
	void saveTest() {

		Books book = bookService.save(UtilTest.createBook());

		Assertions.assertThat(book.getTitle()).isNotNull().isEqualTo(UtilTest.createBook().getTitle());

	}

	@Test
	@DisplayName("UpdateTest() => pdates book when successful")
	void updateTest() {

		Books updateF = UtilTest.updateBook();

		Assertions.assertThatCode(() -> bookService.update(updateF)).doesNotThrowAnyException();

	}

	@Test
	@DisplayName("deleteByIdTest() => Removes books wnhen sucessfull")
	void deleteByIdTest() {

		Assertions.assertThatCode(() -> bookService.deleteById(1)).doesNotThrowAnyException();

	}

}
