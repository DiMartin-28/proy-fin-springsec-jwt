package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import com.dimartin.springsecurity21.model.Author;
import com.dimartin.springsecurity21.model.Permission;

public interface IAuthorService {

	List<Author> findAll();
	Optional<Author> findById(Long id);
	Author save(Author author);
	void deleteById(Long id);
	Author update(Author author);


}
