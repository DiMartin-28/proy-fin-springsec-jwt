package com.dimartin.springsecurity21.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dimartin.springsecurity21.model.Author;

public interface IAuthorRepository extends JpaRepository<Author, Long>{

}
