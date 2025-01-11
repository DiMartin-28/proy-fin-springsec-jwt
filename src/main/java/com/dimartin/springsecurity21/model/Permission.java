package com.dimartin.springsecurity21.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="permissions")
public class Permission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false) // un permiso nunca puede ser null
	private String permission_name;
	
	public Permission() {
		super();
	}
	
	public Permission(Long id, String permission_name) {
		super();
		this.id = id;
		this.permission_name = permission_name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPermission_name() {
		return permission_name;
	}
	
	public void setPermission_name(String permission_name) {
		this.permission_name = permission_name;
	}
}
