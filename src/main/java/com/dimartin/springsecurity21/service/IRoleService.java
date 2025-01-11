package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import com.dimartin.springsecurity21.model.Role;

public interface IRoleService {
	
	List<Role> findAll();
	Optional<Role> findById(Long id);
	Role save(Role role);
	void deleteById(Long id);
	Role update(Role role);
	void deleteRole(Long roleId);

}
