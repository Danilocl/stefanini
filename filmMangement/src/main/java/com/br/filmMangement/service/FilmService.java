package com.br.filmMangement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.filmMangement.model.Films;
import com.br.filmMangement.repository.FilmRepository;

@Service
public class FilmService {

	@Autowired
	private FilmRepository filmRepository;

	public Films save(Films book) {
		Films newBook = filmRepository.save(book);
		return newBook;
	}

	public List<Films> getAll() {
		List<Films> list = new ArrayList<Films>();
		list = filmRepository.findAll();
		return list;
	}

	public Optional<Films> findById(Integer id) {
		Optional<Films> films = filmRepository.findById(id);
		return films;
	}

	public Films findByTitle(String title) {
		Films films = filmRepository.findByTitle(title);
		return films;
	}

	public void deleteById(Integer id) {
		filmRepository.deleteById(id);
	}

	public Films update(Films film) {
		Films filmUp = filmRepository.save(film);
		return filmUp;
	}

}
