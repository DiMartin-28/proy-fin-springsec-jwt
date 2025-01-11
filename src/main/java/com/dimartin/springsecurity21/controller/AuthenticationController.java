package com.dimartin.springsecurity21.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimartin.springsecurity21.dto.AuthLoginRequestDTO;
import com.dimartin.springsecurity21.dto.AuthResponseDTO;
import com.dimartin.springsecurity21.service.UserDetailsServiceImp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	
	@Autowired
	private UserDetailsServiceImp userDetailsService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO>login(@RequestBody @Valid AuthLoginRequestDTO userRequest){
	
		return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest),HttpStatus.OK);
	}
	

}
