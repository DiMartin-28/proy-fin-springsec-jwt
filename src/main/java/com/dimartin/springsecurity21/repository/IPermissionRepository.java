package com.dimartin.springsecurity21.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dimartin.springsecurity21.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long>{

}
