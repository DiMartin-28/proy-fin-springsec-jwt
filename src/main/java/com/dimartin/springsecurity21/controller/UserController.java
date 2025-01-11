package com.dimartin.springsecurity21.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import com.dimartin.springsecurity21.model.Role;
import com.dimartin.springsecurity21.model.UserSec;
import com.dimartin.springsecurity21.service.IAuthorService;
import com.dimartin.springsecurity21.service.IRoleService;
import com.dimartin.springsecurity21.service.IUserService;

import jakarta.persistence.CascadeType;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("denyAll()") 
public class UserController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAuthorService authorService;
	
	
	@GetMapping	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')") 
	public ResponseEntity<List<UserSec>> getAllUsers() {
		List<UserSec> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
		Optional<UserSec> user = userService.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
		System.out.println("Entra en el ednpoint!");
		System.out.println("usuario creado con exito!");
		Set<Role> roleList = new HashSet<Role>();
		Role readRole;

		userSec.setPassword(userService.encriptPassword(userSec.getPassword()));
		
		for (Role role : userSec.getRolesList()) {
			readRole = roleService.findById(role.getId()).orElse(null);
			if (readRole != null) {
				roleList.add(readRole);
			}
		}

		if (!roleList.isEmpty()) {
			userSec.setRolesList(roleList);

			UserSec newUser = userService.save(userSec);
		
			return ResponseEntity.ok(newUser);
		}
	
		return null;
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserSec> updateUser(@PathVariable Long id, @RequestBody UserSec newUser){
		
		UserSec user = userService.findById(id).orElseThrow(()->new RuntimeException("User not found"));
		
		if(user != null && newUser.getId() == id) {
			
			user = newUser;
			user.setPassword(userService.encriptPassword(user.getPassword()));
			userService.update(user);
			return ResponseEntity.ok(user);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
	} 

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
	
		String mensaje = "";
		
		UserSec user = userService.findById(id).orElse(null);
		
		if(user != null){
			userService.deleteById(id);
			mensaje = "usuario eliminado correctamente";
		}
		else {
			mensaje = "ocurrio un error al eliminar el usuario";
		}
		
		return ResponseEntity.ok(mensaje);
	}
}
