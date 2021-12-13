package com.br.filmMangement.controller;

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

import com.br.filmMangement.model.Films;
import com.br.filmMangement.repository.FilmRepository;
import com.br.filmMangement.service.FilmService;
import com.br.filmMangement.util.UtilTest;

@ExtendWith(SpringExtension.class)
public class FilmControllerTest {

	@Mock
	private FilmService filmService;

	@Mock
	private FilmRepository filmRepository;

	@InjectMocks
	private FilmController filmController;

	@BeforeEach
	void setUp() {

		BDDMockito.when(filmService.getAll()).thenReturn(List.of(UtilTest.createFilm()));

		BDDMockito.when(filmService.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(UtilTest.createFilm()));

		BDDMockito.when(filmService.findByTitle(ArgumentMatchers.anyString())).thenReturn(UtilTest.createFilm());

		BDDMockito.when(filmService.save(ArgumentMatchers.any(Films.class))).thenReturn(UtilTest.createFilm());

		BDDMockito.when(filmRepository.save(ArgumentMatchers.any(Films.class))).thenReturn(UtilTest.createFilm());

		BDDMockito.when(filmService.save(ArgumentMatchers.any(Films.class))).thenReturn(UtilTest.createFilm());

		BDDMockito.doNothing().when(filmService).deleteById(ArgumentMatchers.anyInt());
	}

	@Test
	@DisplayName("Returns list of films when successful")
	void getAllTest() {
		String expectedTitle = UtilTest.createFilm().getTitle();

		List<Films> films = filmController.getAll().getBody();

		Assertions.assertThat(films).isNotNull().isNotEmpty();

		Assertions.assertThat(films.get(0).getTitle()).isEqualTo(expectedTitle);
	}

	@Test
	@DisplayName("Find by id when Successful")
	void findByIdTest() {
		Integer expectedId = UtilTest.createFilm().getId();

		Optional<Films> filmSaved = this.filmController.findByID(1).getBody();

		Assertions.assertThat(filmSaved).isNotNull();

		Assertions.assertThat(filmSaved.get().getId()).isNotNull().isEqualTo(expectedId);

	}

	@Test
	@DisplayName("Find by title when Successful")
	void findByTitleTest() {
		String expectedTitle = UtilTest.createFilm().getTitle();

		Films film = this.filmController.findByTitle("Test").getBody();

		Assertions.assertThat(film).isNotNull();

		Assertions.assertThat(film.getTitle()).isEqualTo(expectedTitle);

	}

	@Test
	@DisplayName("Find by title when Successful")
	void findByTitleEmptyTest() {

		Films film = this.filmController.findByTitle("asasad").getBody();

		Assertions.assertThat(film).isNotNull();

	}

	@Test
	@DisplayName("Returns film when successful")
	void save() {

		Films film = filmController.save(UtilTest.createFilm(), true).getBody();

		Assertions.assertThat(film.getTitle()).isNotNull().isEqualTo(UtilTest.createFilm().getTitle());

	}

	@Test
	@DisplayName("delteTest() => Returns film when successful")
	void delteTest() {

		Assertions.assertThatCode(() -> filmController.deleteByID(1)).doesNotThrowAnyException();

		ResponseEntity<Optional<Films>> film = filmController.deleteByID(1);

		Assertions.assertThat(film).isNotNull();

		Assertions.assertThat(film.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	@DisplayName("updateTest() => updates film when successful")
	void updateTest() {

		Assertions.assertThatCode(() -> filmController.update(UtilTest.createFilm().getId(), UtilTest.updateFilm()))
				.doesNotThrowAnyException();

		ResponseEntity<Films> entity = filmController.update(UtilTest.createFilm().getId(), UtilTest.updateFilm());

		Assertions.assertThat(entity).isNotNull();

		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
