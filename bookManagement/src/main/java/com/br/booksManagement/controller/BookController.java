package com.br.booksManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.booksManagement.Exception.ControllerException;
import com.br.booksManagement.dto.FilmDTO;
import com.br.booksManagement.model.Books;
import com.br.booksManagement.repository.BookRepository;
import com.br.booksManagement.service.BookService;
import com.br.booksManagement.util.FilmApi;
import com.br.booksManagement.util.MessangerErrorEnum;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	@PostMapping
	@Operation(summary = "Create new Book")
	public ResponseEntity<Books> save(@RequestBody Books books, boolean test) {
		Books bookByTitle = bookService.findByTitle(books.getTitle());
		if (bookByTitle != null && test == false) {
			throw new ControllerException(HttpStatus.INTERNAL_SERVER_ERROR, MessangerErrorEnum.REPEATED.getMessenger());
		} else {			
			bookRepository.save(books);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
	}

	@GetMapping
	@Operation(summary = "Search all books")
	public ResponseEntity<List<Books>> getAll() {
		List<Books> list = new ArrayList<Books>();
		list = bookService.getAll();
		if (list.size() == 0) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.EMPTY.getMessenger());
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/id/{id}")
	@Operation(summary = "Search book by id")
	public ResponseEntity<Optional<Books>> findByID(@PathVariable Integer id) {
		Optional<Books> books;
		books = bookService.findById(id);
		if (books.isEmpty()) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		} else {
			return new ResponseEntity<Optional<Books>>(books, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/api/boths/{title}")
	@Operation(summary = "look for the corresponding book and movie ")
	public ResponseEntity<Object> findBookFilmByTitle(@PathVariable String title) {
		Books books;
		books = bookService.findByTitle(title);
		if (books == null) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		} else {
			FilmDTO films = FilmApi.getFilm(title);

			if (films == null) {
				throw new ControllerException(HttpStatus.NOT_FOUND,
						MessangerErrorEnum.RELATEDFILMNOTFOUND.getMessenger());
			}

			Object[] objectArr = new Object[2];

			objectArr[0] = books;
			objectArr[1] = films;

			return new ResponseEntity<>(objectArr, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/title/{title}")
	@Operation(summary = "Search book by title")
	public ResponseEntity<Books> findByTitle(@PathVariable String title) {
		Books books;
		books = bookService.findByTitle(title);
		if (books == null) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		} else {

			return new ResponseEntity<>(books, HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "/{id}")
	@Operation(summary = "Delete book by ID")
	public ResponseEntity<Optional<Books>> deleteByID(@PathVariable Integer id) {
		try {
			bookService.deleteById(id);
			return new ResponseEntity<Optional<Books>>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		}
	}

	@PutMapping(path = "/{id}")
	@Operation(summary = "updates an existing book")
	public ResponseEntity<Books> update(@PathVariable Integer id, @RequestBody Books books) {
		return bookService.findById(id).map(book -> {
			book.setTitle(books.getTitle());
			book.setAuthor(books.getAuthor());
			book.setCountry(books.getCountry());
			book.setReleaseDate(books.getReleaseDate());
			book.setPublisher(books.getPublisher());
			book.setType(books.getType());
			Books bookUp = bookService.save(book);
			return ResponseEntity.ok().body(bookUp);
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(path = "/api/search/{title}")
	@Operation(summary = "search for the book and its corresponding film")
	public ResponseEntity<FilmDTO> findByFilmTitle(@PathVariable String title) {

		FilmDTO films = FilmApi.getFilm(title);

		if (films == null) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.FILMNOTFOUND.getMessenger());
		}

		return new ResponseEntity<>(films, HttpStatus.OK);
	}
}
