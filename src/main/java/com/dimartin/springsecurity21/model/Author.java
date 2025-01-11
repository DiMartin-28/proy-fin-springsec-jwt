package com.dimartin.springsecurity21.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="authors")
public class Author {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name; 
	
	private String biography;
	
	@OneToOne 
	@JoinColumn(name = "user_id") 
	@JsonBackReference    // sin eso me hace un bucle infinito
	private UserSec user;
	 
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL) 
	@JsonManagedReference
	private List<Post> postsList;
	
	public Author() {
	}


	public Author(Long id, String name, UserSec user, List<Post> postsList, String biography) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.postsList = postsList;
		this.biography = biography;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getBiography() {
		return biography;
	}


	public void setBiography(String biography) {
		this.biography = biography;
	}

	

	public UserSec getUser() {
		return user;
	}

	public void setUser(UserSec user) {
		this.user = user;
	}

	public List<Post> getPostsList() {
		return postsList;
	}

	public void setPostsList(List<Post> postsList) {
		this.postsList = postsList;
	}
}
