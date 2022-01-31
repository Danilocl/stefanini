package com.br.booksManagement.util;

import com.br.booksManagement.model.Books;

public class UtilTest {

	public static Books createBook() {
		Books books = new Books();
		books.setId(1);
		books.setTitle("Test");
		books.setAuthor("Test");
		books.setCountry("Test");
		books.setReleaseDate("Test");
		books.setType("Test");
		books.setPublisher("Test");
		return books;
	}

	public static Books updateBook() {
		Books books = new Books();
		books.setId(2);
		books.setTitle("TestUpdate");
		books.setAuthor("TestUpdate");
		books.setCountry("TestUpdate");
		books.setReleaseDate("TestUpdate");
		books.setType("TestUpdate");
		books.setPublisher("TestUpdate");
		return books;
	}

}
