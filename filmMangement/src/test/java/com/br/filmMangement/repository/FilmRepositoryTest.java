package com.br.filmMangement.repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.br.filmMangement.model.Films;
import com.br.filmMangement.util.UtilTest;

@DataJpaTest
public class FilmRepositoryTest {

	@Autowired
	FilmRepository filmRepo;

	@Test
	@DisplayName("Save film when Successful")
	public void saveTest() {
		Films film = filmRepo.save(UtilTest.createFilm());

		Assertions.assertThat(film).isNotNull();
		Assertions.assertThat(film.getId()).isNotNull();
		Assertions.assertThat(film.getTitle()).isEqualTo(UtilTest.createFilm().getTitle());
	}

	@Test
	@DisplayName("Updates film when Successful")
	void updateTest() {

		Films filmTobeSaved = UtilTest.createFilm();

		Films filmSaved = this.filmRepo.save(filmTobeSaved);

		filmSaved.setTitle("");

		Films filmUpdated = this.filmRepo.save(filmSaved);

		Assertions.assertThat(filmUpdated).isNotNull();

		Assertions.assertThat(filmUpdated.getId()).isNotNull();

		Assertions.assertThat(filmUpdated.getTitle()).isEqualTo(filmSaved.getTitle());
	}

	@Test
	@DisplayName("Delete film when Successful")
	void deleteTest() {
		Films filmTobeSaved = UtilTest.createFilm();

		Films filmSaved = this.filmRepo.save(filmTobeSaved);

		this.filmRepo.delete(filmSaved);

		Optional<Films> animeOptional = this.filmRepo.findById(filmSaved.getId());

		Assertions.assertThat(animeOptional).isEmpty();

	}

	@Test
	@DisplayName("Find all films when Successful")
	void getAllTest() {

		Films filmTobeSaved = UtilTest.createFilm();

		this.filmRepo.save(filmTobeSaved);

		List<Films> films = this.filmRepo.findAll();

		Assertions.assertThat(films).isNotEmpty();

	}

	@Test
	@DisplayName("Find by title when Successful")
	void findByTitleTest() {
		Films filmTobeSaved = UtilTest.createFilm();

		Films filmSaved = this.filmRepo.save(filmTobeSaved);

		String title = filmSaved.getTitle();

		Films film = this.filmRepo.findByTitle(title);

		Assertions.assertThat(film).isNotNull();

	}

	@Test
	@DisplayName("Rreturns empty when no film is found")
	void findByTitleEmptyTest() {
		Films films = this.filmRepo.findByTitle("sasaas");

		Assertions.assertThat(films).isNull();
	}

	@Test
	@DisplayName("Find by id when Successful")
	void findByIdTest() {
		Films filmTobeSaved = UtilTest.createFilm();

		Films filmSaved = this.filmRepo.save(filmTobeSaved);

		Integer id = filmSaved.getId();

		Optional<Films> film = this.filmRepo.findById(id);

		Assertions.assertThat(film).isNotEmpty().contains(filmSaved);

	}

	@Test
	@DisplayName("Find by id when Successful")
	void findByIdEmptyTest() {

		Random randomId = new Random();

		System.out.println(randomId.nextInt());

		Optional<Films> film = this.filmRepo.findById(randomId.nextInt());

		Assertions.assertThat(film).isEmpty();

	}

}
