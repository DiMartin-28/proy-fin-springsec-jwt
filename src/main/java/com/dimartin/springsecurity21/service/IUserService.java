package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import com.dimartin.springsecurity21.model.UserSec;


public interface IUserService {
	
	public List<UserSec> findAll();
	public Optional<UserSec> findById(Long id);
	public UserSec save(UserSec userSec);
	public void deleteById(Long id);
	public UserSec update(UserSec userSec);
	// metodo para encriptar el password del usuario
	public String encriptPassword(String password);

}
