package com.dimartin.springsecurity21.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class UserSec {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	private boolean enabled = true; // aca al no usar lombok lo tuve que setear en true, de lo contrario daba siempre false
	private boolean accountNotExpired;
	private boolean accountNotLocked;
	private boolean credentialNotExpired;
//	private boolean authorBool;
	
	@ManyToMany(fetch = FetchType.EAGER) // , cascade = CascadeType.ALL 
	@JoinTable(name="user_roles",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> rolesList = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "author_id")
//	@JsonManagedReference  // esto me estaba provocando el 415
	private Author author;

	public UserSec() {
	}

	

	public UserSec(Long id, String username, String password, boolean enabled, boolean accountNotExpired,
			boolean accountNotLocked, boolean credentialNotExpired, Set<Role> rolesList, Author author) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNotExpired = accountNotExpired;
		this.accountNotLocked = accountNotLocked;
		this.credentialNotExpired = credentialNotExpired;
		this.rolesList = rolesList;
		this.author = author;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNotExpired() {
		return accountNotExpired;
	}

	public void setAccountNotExpired(boolean accountNotExpired) {
		this.accountNotExpired = accountNotExpired;
	}

	public boolean isAccountNotLocked() {
		return accountNotLocked;
	}

	public void setAccountNotLocked(boolean accountNotLocked) {
		this.accountNotLocked = accountNotLocked;
	}

	public boolean isCredentialNotExpired() {
		return credentialNotExpired;
	}

	public void setCredentialNotExpired(boolean credentialNotExpired) {
		this.credentialNotExpired = credentialNotExpired;
	}

	public Set<Role> getRolesList() {
		return rolesList;
	}

	public void setRolesList(Set<Role> rolesList) {
		this.rolesList = rolesList;
	}



	public Author getAuthor() {
		return author;
	}



	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
}
