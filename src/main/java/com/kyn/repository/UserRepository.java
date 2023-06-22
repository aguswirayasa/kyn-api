package com.kyn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kyn.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByuserName(String username);
	public User findByProviderId(String providerId);
	
	 @Modifying
	 @Query("UPDATE User u SET u.profileImage = :profileImage WHERE u.id = :id")
	  void updateProfileImageById(@Param("profileImage") byte[] profileImage, @Param("id") Long id);
	
}
