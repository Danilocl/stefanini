package com.br.filmMangement.util;

import com.br.filmMangement.model.Films;

public class UtilTest {

	public static Films createFilm() {
		Films films = new Films();
		films.setId(1);
		films.setTitle("Test");
		films.setAuthor("Test");
		films.setCountry("Test");
		films.setReleaseDate("Test");
		films.setType("Test");
		films.setCinematography("test");
		return films;
	}

	public static Films updateFilm() {
		Films films = new Films();
		films.setId(2);
		films.setTitle("TestUpdate");
		films.setAuthor("TestUpdate");
		films.setCountry("TestUpdate");
		films.setReleaseDate("TestUpdate");
		films.setType("TestUpdate");
		films.setCinematography("TestUpdate");
		return films;
	}

}
