package com.dimartin.springsecurity21.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String role;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "roles_permissions", joinColumns=@JoinColumn(name="role_id"),
	inverseJoinColumns = @JoinColumn(name="permission_id"))
	private Set<Permission> permissionsList = new HashSet<>();

	public Role() {
		super();
	}

	public Role(Long id, String role, Set<Permission> permissionsList) {
		super();
		this.id = id;
		this.role = role;
		this.permissionsList = permissionsList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Permission> getPermissionsList() {
		return permissionsList;
	}

	public void setPermissionsList(Set<Permission> permissionsList) {
		this.permissionsList = permissionsList;
	}
}
