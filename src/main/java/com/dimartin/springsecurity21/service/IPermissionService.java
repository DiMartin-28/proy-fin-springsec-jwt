package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import com.dimartin.springsecurity21.model.Permission;

public interface IPermissionService {
	
	List<Permission> findAll();
	Optional<Permission> findById(Long id);
	Permission save(Permission permission);
	void deleteById(Long id);
	Permission update(Permission permission);

}
