package com.br.booksManagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.br.booksManagement.model.Books;
import com.br.booksManagement.util.UtilTest;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	BookRepository bookRepo;

	@Test
	@DisplayName("saveTest() => Save book when Successful")
	public void saveTest() {
		Books books = bookRepo.save(UtilTest.createBook());

		Assertions.assertThat(books).isNotNull();
		Assertions.assertThat(books.getId()).isNotNull();
		Assertions.assertThat(books.getTitle()).isEqualTo(UtilTest.createBook().getTitle());
	}

	@Test
	@DisplayName("updateTest() => Updates book when Successful")
	void updateTest() {

		Books bookTobeSaved = UtilTest.createBook();

		Books bookSaved = this.bookRepo.save(bookTobeSaved);

		bookSaved.setTitle("");

		Books bookUpdated = this.bookRepo.save(bookSaved);

		Assertions.assertThat(bookUpdated).isNotNull();

		Assertions.assertThat(bookUpdated.getId()).isNotNull();

		Assertions.assertThat(bookUpdated.getTitle()).isEqualTo(bookSaved.getTitle());
	}

	@Test
	@DisplayName("deleteTest() => Delete book when Successful")
	void deleteTest() {
		Books bookTobeSaved = UtilTest.createBook();

		Books bookSaved = this.bookRepo.save(bookTobeSaved);

		this.bookRepo.delete(bookSaved);

		Optional<Books> filmOptional = this.bookRepo.findById(bookSaved.getId());

		Assertions.assertThat(filmOptional).isEmpty();

	}

	@Test
	@DisplayName("getAllTest() => Find all books when Successful")
	void getAllTest() {

		Books bookTobeSaved = UtilTest.createBook();

		this.bookRepo.save(bookTobeSaved);

		List<Books> books = this.bookRepo.findAll();

		Assertions.assertThat(books).isNotEmpty();

	}

	@Test
	@DisplayName("findByTitleTest() => Find by title when Successful")
	void findByTitleTest() {
		Books booksTobeSaved = UtilTest.createBook();

		Books bookSaved = this.bookRepo.save(booksTobeSaved);

		String title = bookSaved.getTitle();

		Books book = this.bookRepo.findByTitle(title);

		Assertions.assertThat(book).isNotNull();

	}

	@Test
	@DisplayName("findByTitleEmptyTest() => Returns empty when no book is found")
	void findByTitleEmptyTest() {
		Books books = this.bookRepo.findByTitle("sasaas");

		Assertions.assertThat(books).isNull();
	}

	@Test
	@DisplayName("findByIdTest() => Find by id when Successful")
	void findByIdTest() {
		Books bookTobeSaved = UtilTest.createBook();

		Books bookSaved = this.bookRepo.save(bookTobeSaved);

		Integer id = bookSaved.getId();

		Optional<Books> book = this.bookRepo.findById(id);

		Assertions.assertThat(book).isNotEmpty().contains(bookSaved);

	}

	@Test
	@DisplayName("findByIdEmptyTest() => Find by id when Successful")
	void findByIdEmptyTest() {

		Random randomId = new Random();

		System.out.println(randomId.nextInt());

		Optional<Books> book = this.bookRepo.findById(randomId.nextInt());

		Assertions.assertThat(book).isEmpty();

	}

}
