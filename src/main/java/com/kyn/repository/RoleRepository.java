package com.kyn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kyn.entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}