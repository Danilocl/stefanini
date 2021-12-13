package com.br.booksManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.booksManagement.model.Books;
import com.br.booksManagement.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Books save(Books book) {
		Books newBook = bookRepository.save(book);
		return newBook;
	}

	public List<Books> getAll() {
		List<Books> list = new ArrayList<Books>();
		list = bookRepository.findAll();
		return list;
	}

	public Optional<Books> findById(Integer id) {
		Optional<Books> books = bookRepository.findById(id);
		return books;
	}

	public Books findByTitle(String title) {
		Books books = bookRepository.findByTitle(title);
		return books;
	}

	public void deleteById(Integer id) {
		bookRepository.deleteById(id);
	}

	public Books update(Books book) {
		Books bookUp = bookRepository.save(book);
		return bookUp;
	}

}
