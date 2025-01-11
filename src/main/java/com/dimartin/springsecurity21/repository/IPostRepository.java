package com.dimartin.springsecurity21.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dimartin.springsecurity21.model.Post;

public interface IPostRepository extends JpaRepository<Post, Long>{

}
