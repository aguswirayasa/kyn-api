package com.kyn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kyn.entity.Role;
import com.kyn.entity.User;
import com.kyn.repository.RoleRepository;
import com.kyn.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User getUserByProviderId(String providerId) {
		return userRepo.findByProviderId(providerId);
	}
	public User saveUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role role = roleRepo.findByName("USER");
		user.setRole(role);
		return userRepo.save(user);
		
	}
	
	public String UpdateProfile(User user) {
		try {
			userRepo.save(user);
			return "Update succesfull";
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public User findByUsername(String username) {
		return userRepo.findByuserName(username);
	}
	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}
	public boolean existsByUsername(String username) {
		User user = userRepo.findByuserName(username);
		if(user!=null) {
			return true;
		}
		return false;
	}
	
	public List<User> getAllUser(){
		return userRepo.findAll();
		
	}
	
	public String deleteById(Long id) {
		try {
			userRepo.deleteById(id);
			return "Delete succesfull";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
	}
	
	public String updateProfileImage(byte[]profileImage,Long id) {
		try {
			userRepo.updateProfileImageById(profileImage, id);
			return "update profile succesfull";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return null;
		
	}
}
