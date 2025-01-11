package com.dimartin.springsecurity21.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimartin.springsecurity21.model.Author;
import com.dimartin.springsecurity21.model.Post;
import com.dimartin.springsecurity21.service.IAuthorService;
import com.dimartin.springsecurity21.service.IPostService;

@RestController
@RequestMapping("/api/posts")
@PreAuthorize("denyAll()") 
public class PostController {

	@Autowired
	private IPostService postService;
	
	@Autowired
	private IAuthorService authorService;
	
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER','AUTHOR')")
	public ResponseEntity<List<Post>> getAllPost(){
		
		List<Post> postList = postService.findAll();
		return ResponseEntity.ok(postList);
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER','AUTHOR')")
	public ResponseEntity<Post> getPostById(@PathVariable Long id){
		
		Post post = postService.findById(id).orElse(null);
		
		return ResponseEntity.ok(post);
	}
	
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePostById(@PathVariable Long id) {
		
		String message = "Hubo un error al eliminar el post";
		Post post = postService.findById(id).orElse(null);
		
		if(post != null) {
			postService.deleteById(id);
			message = "Post eliminado correctamente!";
			
		}
		return ResponseEntity.ok(message);
	}
	
	
	// endpoint para modificar un post
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
	public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post newPost ){
		
		Post post = postService.findById(id).orElse(null);
		
		if(post != null && newPost.getId() == id){
			post = newPost;
			postService.update(post);
		}
		
		return ResponseEntity.ok(post);
	}

	
	@PostMapping	
	@PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
	
		Long authorId = post.getAuthor().getId();
		Author author = authorService.findById(authorId).orElse(null);
	
		if(author != null){
			
			Post newPost = postService.save(post);
			
			return ResponseEntity.ok(newPost);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
