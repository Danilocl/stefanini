package com.br.filmMangement.service;

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
import com.br.filmMangement.model.Films;
import com.br.filmMangement.repository.FilmRepository;
import com.br.filmMangement.util.UtilTest;

@ExtendWith(SpringExtension.class)
public class FilmServiceTest {

	@InjectMocks
	private FilmService filmService;

	@Mock
	private FilmRepository filmRepository;

	@BeforeEach
	void setUp() {
		PageImpl<Films> filmPage = new PageImpl<>(List.of(UtilTest.createFilm()));

		BDDMockito.when(filmRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(filmPage);

		BDDMockito.when(filmRepository.findAll()).thenReturn(List.of(UtilTest.createFilm()));

		BDDMockito.when(filmRepository.findById(ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(UtilTest.createFilm()));

		BDDMockito.when(filmRepository.findByTitle(ArgumentMatchers.anyString())).thenReturn(UtilTest.createFilm());

		BDDMockito.when(filmRepository.save(ArgumentMatchers.any(Films.class))).thenReturn(UtilTest.createFilm());

		BDDMockito.doNothing().when(filmRepository).delete(ArgumentMatchers.any(Films.class));
	}

	@Test
	@DisplayName("Returns list of films when successful")
	void listAllTest() {
		String expectedTitle = UtilTest.createFilm().getTitle();

		List<Films> films = filmService.getAll();

		Assertions.assertThat(films).isNotNull().isNotEmpty();

		Assertions.assertThat(films.get(0).getTitle()).isEqualTo(expectedTitle);
	}

	@Test
	@DisplayName("Returns film by id when successful")
	void findByIdTest() {

		List<Films> filmsList = filmService.getAll();

		Optional<Films> films = filmService.findById(filmsList.get(0).getId());

		Assertions.assertThat(films).isNotNull();

		Assertions.assertThat(films.get().getId()).isNotNull().isEqualTo(filmsList.get(0).getId());
	}

	@Test
	@DisplayName("Returns film by id when successful")
	void findByTitleTest() {

		List<Films> filmsList = filmService.getAll();

		Films films = filmService.findByTitle(filmsList.get(0).getTitle());

		Assertions.assertThat(films).isNotNull();

		Assertions.assertThat(films.getTitle()).isNotNull().isEqualTo(filmsList.get(0).getTitle());
	}

	@Test
	@DisplayName("Save film when successful")
	void saveTest() {

		Films film = filmService.save(UtilTest.createFilm());

		Assertions.assertThat(film.getTitle()).isNotNull().isEqualTo(UtilTest.createFilm().getTitle());

	}

	@Test
	@DisplayName("Updates film when successful")
	void updateTest() {

		Films updateF = UtilTest.updateFilm();

		Assertions.assertThatCode(() -> filmService.update(updateF)).doesNotThrowAnyException();

	}

	@Test
	@DisplayName("Removes film wnhen sucessfull")
	void deleteByIdTest() {

		Assertions.assertThatCode(() -> filmService.deleteById(1)).doesNotThrowAnyException();

	}
}
