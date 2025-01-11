package com.dimartin.springsecurity21.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimartin.springsecurity21.model.Permission;
import com.dimartin.springsecurity21.service.IPermissionService;

@RestController
@RequestMapping("/api/permissions")
@PreAuthorize("denyAll()")
public class PermissionController {
	
	@Autowired
	private IPermissionService permissionService;
	
	@GetMapping 
	@PreAuthorize("hasRole('ADMIN','USER')")
	public ResponseEntity<List<Permission>> getAllPermissions(){
		List<Permission> permissions = permissionService.findAll();
		return ResponseEntity.ok(permissions);
	}
	
	@GetMapping("/{id}")	
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Permission> getPermissionById(@PathVariable Long id){
		Optional<Permission> permission = permissionService.findById(id);

		return permission.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	
	@PostMapping	
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
		Permission newPermission = permissionService.save(permission);
		return ResponseEntity.ok(newPermission);
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission newPer){
		
		Permission per = permissionService.findById(id).orElse(null);
		
		if(per != null && newPer.getId() == id){
			
			per = newPer;
			permissionService.save(newPer);
		}
		
		return ResponseEntity.ok(per);
	}
	
	
	@DeleteMapping("/{id}") 
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteById(@PathVariable Long id){
		
		String mensaje = "";
		
		Permission per = permissionService.findById(id).orElse(null);
		if(per != null) {
			permissionService.deleteById(id);
			mensaje = "Permiso eliminado correctamente";
		}
		else {
			mensaje = "No fue posible eliminar el permiso";
		}
		
		return mensaje;
	}
}
