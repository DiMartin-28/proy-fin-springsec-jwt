package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import com.dimartin.springsecurity21.model.Post;

public interface IPostService {

	List<Post> findAll();
	Optional<Post> findById(Long id);
	Post save(Post post);
	void deleteById(Long id);
	Post update(Post post);
	
}
