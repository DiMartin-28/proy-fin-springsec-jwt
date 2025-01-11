package com.dimartin.springsecurity21.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.dimartin.springsecurity21.model.Role;
import com.dimartin.springsecurity21.model.UserSec;
import com.dimartin.springsecurity21.service.IAuthorService;
import com.dimartin.springsecurity21.service.IUserService;

@RestController
@RequestMapping("/api/authors")
@PreAuthorize("denyAll()") 
public class AuthorController {
	
	@Autowired
	private IAuthorService authorService;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<Author>> getAllAuthors(){
		
		List<Author> authorsList = authorService.findAll();
		
		return ResponseEntity.ok(authorsList);
		
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','AUTHOR')") // ACORDATE DE SACAR AUTHOR
	public ResponseEntity<Author> getAuthor(@PathVariable Long id){
		
		Author author = authorService.findById(id).orElse(null);
		
		return ResponseEntity.ok(author);
	}
	

	// endpoint para modificar los datos de un autor
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Author> updateUser(@PathVariable Long id, @RequestBody Author newAuthor){
		// ESTE HAY QUE DEJARLO ASI COMO ESTA
		System.out.println("entrando en el endpoint");
		Author author = authorService.findById(id).orElse(null);

		if(author != null && newAuthor.getId() == id){
			
			author = newAuthor;
			authorService.update(author);
			
		}
		return ResponseEntity.ok(author);
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Author> createAuthor(@RequestBody Author author){
		
		Long userId = author.getUser().getId();
		
		UserSec user = userService.findById(userId).orElse(null);
		Set<Role> rolesList = user.getRolesList();
		
		for(Role r : rolesList) {
			if(r.getRole().contains("AUTHOR")) {
				System.out.println("El role es : " + r.getRole() );
				Author newAuthor = author;
				authorService.save(newAuthor);
				return ResponseEntity.ok(newAuthor);
			} 
		} 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteAuthor (@PathVariable Long id) {
		
		Author author = authorService.findById(id).orElse(null);
		
		Long userId = author.getUser().getId();
		UserSec user = userService.findById(userId).orElse(null);
		
		if(author != null) {
			
			user.setAuthor(null); 
			authorService.deleteById(id);
			return ResponseEntity.ok("Autor eliminado correctamente!");
		}
		
		return ResponseEntity.ok("Error al eliminar el autor!");
	}
}
