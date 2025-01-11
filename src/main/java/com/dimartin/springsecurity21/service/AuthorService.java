package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimartin.springsecurity21.model.Author;
import com.dimartin.springsecurity21.model.Permission;
import com.dimartin.springsecurity21.repository.IAuthorRepository;

@Service
public class AuthorService implements IAuthorService{

	@Autowired
	private IAuthorRepository authorRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	@Override
	public Optional<Author> findById(Long id) {
		return authorRepository.findById(id);
	}

	@Override
	public Author save(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public void deleteById(Long id) {
		authorRepository.deleteById(id);
	}

	@Override
	public Author update(Author author) {
		return authorRepository.save(author);
	}
	
	public void validateAuthor(Long id) {
		
	}

	

}
