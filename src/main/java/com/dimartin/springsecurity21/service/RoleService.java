package com.dimartin.springsecurity21.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimartin.springsecurity21.model.Role;
import com.dimartin.springsecurity21.repository.IRoleRepository;

import jakarta.persistence.EntityManager;

@Service
public class RoleService implements IRoleService{
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired 
	private EntityManager entityManager;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public Role update(Role role) {
		return roleRepository.save(role);
	}
	
	
	@Transactional 
	public void deleteRole(Long roleId) { 
		// eliminamos las relaciones dependientes en user_roles 
		String deleteUserRolesQuery = "DELETE FROM user_roles WHERE role_id = :roleId"; 
		entityManager.createNativeQuery(deleteUserRolesQuery).setParameter("roleId", roleId) .executeUpdate(); 
		
		// eliminar permisos asociados al rol 
		Role role = roleRepository.findById(roleId) .orElseThrow(() -> new RuntimeException("Role not found")); 
		
		// aca limpiamos la lista de permisos para evitar integridad referencial
		role.getPermissionsList().clear(); 
		
		roleRepository.save(role); 
		
		// ahora eliminar el rol 
		roleRepository.deleteById(roleId);
	}
}
