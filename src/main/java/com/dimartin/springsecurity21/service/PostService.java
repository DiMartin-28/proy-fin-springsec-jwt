package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimartin.springsecurity21.model.Post;
import com.dimartin.springsecurity21.repository.IPostRepository;

@Service
public class PostService implements IPostService{
	
	@Autowired
	private IPostRepository postRepository;

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public Post update(Post post) {
		return postRepository.save(post);
	}

}
