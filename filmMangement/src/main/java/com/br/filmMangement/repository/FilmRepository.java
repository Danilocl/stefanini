package com.br.filmMangement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.filmMangement.model.Films;

public interface FilmRepository extends JpaRepository<Films, Integer> {

	Films findByTitle(String title);

}
