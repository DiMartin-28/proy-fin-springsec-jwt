package com.dimartin.springsecurity21.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dimartin.springsecurity21.model.UserSec;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long>{
	
	Optional<UserSec> findUserEntityByUsername(String username);
}
