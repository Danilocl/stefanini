package com.br.filmMangement.controller;

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

import com.br.filmMangement.dto.BookDTO;
import com.br.filmMangement.exception.ControllerException;
import com.br.filmMangement.model.Films;
import com.br.filmMangement.repository.FilmRepository;
import com.br.filmMangement.service.FilmService;
import com.br.filmMangement.util.BookApi;
import com.br.filmMangement.util.MessangerErrorEnum;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/films")
public class FilmController {

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private FilmService filmService;

	@PostMapping
	@Operation(summary = "Create new Film")
	public ResponseEntity<Films> save(@RequestBody Films film, boolean test) {
		Films filmByTitle = filmService.findByTitle(film.getTitle());
		if (filmByTitle != null && test == false) {
			throw new ControllerException(HttpStatus.INTERNAL_SERVER_ERROR, MessangerErrorEnum.REPEATED.getMessenger());
		} else {			
			filmRepository.save(film);
			return new ResponseEntity<>(film, HttpStatus.OK);
		}
	}

	@GetMapping
	@Operation(summary = "Search all films")
	public ResponseEntity<List<Films>> getAll() {
		List<Films> list = new ArrayList<Films>();
		list = filmService.getAll();
		if (list.size() == 0) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.EMPTY.getMessenger());
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/id/{id}")
	@Operation(summary = "Search film by id")
	public ResponseEntity<Optional<Films>> findByID(@PathVariable Integer id) {
		Optional<Films> films;
		films = filmService.findById(id);
		if (films.isEmpty()) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		} else {
			return new ResponseEntity<Optional<Films>>(films, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/api/boths/{title}")
	@Operation(summary = "search for the movie and its corresponding book")
	public ResponseEntity<Object> findFilmBookByTitle(@PathVariable String title) {
		Films films;
		films = filmService.findByTitle(title);
		if (films == null) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		} else {
			BookDTO books = BookApi.getBook(title);

			if (books == null) {
				throw new ControllerException(HttpStatus.NOT_FOUND,
						MessangerErrorEnum.RELATEDBOOKNOTFOUND.getMessenger());
			}

			Object[] objectArr = new Object[2];

			objectArr[0] = films;
			objectArr[1] = books;

			return new ResponseEntity<>(objectArr, HttpStatus.OK);
		}
	}

	@GetMapping(path = "/title/{title}")
	@Operation(summary = "Search film by title")
	public ResponseEntity<Films> findByTitle(@PathVariable String title) {
		Films films;
		films = filmService.findByTitle(title);
		if (films == null) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		} else {

			return new ResponseEntity<>(films, HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "/{id}")
	@Operation(summary = "Delete film by ID")
	public ResponseEntity<Optional<Films>> deleteByID(@PathVariable Integer id) {
		try {
			filmService.deleteById(id);
			return new ResponseEntity<Optional<Films>>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.NOTFOUND.getMessenger());
		}
	}

	@PutMapping(path = "/{id}")
	@Operation(summary = "updates an existing film")
	public ResponseEntity<Films> update(@PathVariable Integer id, @RequestBody Films filmBody) {
		return filmService.findById(id).map(film -> {
			film.setTitle(filmBody.getTitle());
			film.setAuthor(filmBody.getAuthor());
			film.setCountry(filmBody.getCountry());
			film.setReleaseDate(filmBody.getReleaseDate());
			film.setAuthor(filmBody.getCinematography());
			film.setType(filmBody.getType());
			Films newFilm = filmService.save(film);
			return ResponseEntity.ok().body(newFilm);
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(path = "/api/search/{title}")
	@Operation(summary = "Search by book in the booksAPI")
	public ResponseEntity<BookDTO> findByBookTitle(@PathVariable String title) {

		BookDTO books = BookApi.getBook(title);

		if (books == null) {
			throw new ControllerException(HttpStatus.NOT_FOUND, MessangerErrorEnum.BOOKNOTFOUND.getMessenger());
		}

		return new ResponseEntity<>(books, HttpStatus.OK);
	}
}
