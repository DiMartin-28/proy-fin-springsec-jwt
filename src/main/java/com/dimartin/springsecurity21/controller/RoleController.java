package com.dimartin.springsecurity21.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.dimartin.springsecurity21.model.Permission;
import com.dimartin.springsecurity21.model.Role;
import com.dimartin.springsecurity21.service.IPermissionService;
import com.dimartin.springsecurity21.service.IRoleService;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("denyAll()") 
public class RoleController {
	
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permiService;
	

	@GetMapping 
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<Role>> getAllRoles(){
		List<Role> roles = roleService.findAll();
		return ResponseEntity.ok(roles);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id){
		Optional<Role> role = roleService.findById(id);
		return role.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Role> createRole(@RequestBody Role role){
		
		Set<Permission> permiList = new HashSet<Permission>();
		Permission readPermission; 
		
		for(Permission per : role.getPermissionsList()) {
	
			readPermission = permiService.findById(per.getId()).orElse(null);

			if(readPermission != null) {
				permiList.add(readPermission);
			}
		}
		role.setPermissionsList(permiList);
		Role newRole = roleService.save(role);
		return ResponseEntity.ok(newRole);
	}

	
	//Agregamos end-point de UPDATE
    @PutMapping("/{id}")  
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {

        Role rol = roleService.findById(id).orElse(null);
        
        if (rol!=null && role.getId() == id) {
            rol = role;
            roleService.update(rol);
     
        }
        return ResponseEntity.ok(rol);
    }
    

    @DeleteMapping("/{roleId}") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) { 
    	roleService.deleteRole(roleId);
    	return ResponseEntity.noContent().build();
    }
}
