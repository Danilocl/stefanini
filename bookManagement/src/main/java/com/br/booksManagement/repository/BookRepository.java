package com.br.booksManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.booksManagement.model.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {

	Books findByTitle(String title);
}
