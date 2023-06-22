package com.kyn.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kyn.entity.User;
import com.kyn.request.UpdateRequest;
import com.kyn.service.UserService;

@RestController
@RequestMapping("/api/")
public class ApiController {
	
	@Autowired 
	UserService userService;
	
	@PostMapping("edit-profile")
	public ResponseEntity<?> editProfile (@RequestBody UpdateRequest user){
		System.out.println(user.getUsername());
		User updateProfile = userService.findByUsername(user.getUsername());
		updateProfile.setFirstName(user.getFirstName());
		updateProfile.setLastName(user.getLastName());
		updateProfile.setEmail(user.getEmail());
		updateProfile.setCity(user.getCity());
		updateProfile.setCountry(user.getCountry());
		updateProfile.setBio(user.getBio());
		updateProfile.setUserName(user.getUsername());
		String res = userService.UpdateProfile(updateProfile);
		if(res!=null) {			
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.badRequest().body("Update failed");
		
	}
	@PostMapping("edit-account")
	public ResponseEntity<?> editAccount (@RequestBody UpdateRequest user){
		System.out.println(user.getUsername());
		System.out.println(user.getId());
		Optional<User> updateProfile = userService.findById(user.getId());
		updateProfile.get().setUserName(user.getUsername());
		String res = userService.UpdateProfile(updateProfile.get());
		if(res!=null) {			
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.badRequest().body("Update failed");
		
	}
	@GetMapping("profile")
	public ResponseEntity<?> getProfile (@RequestParam String username){
		User userProfile = userService.findByUsername(username);
		
		return ResponseEntity.ok(userProfile);
		
	}
	@PostMapping("edit-picture")
	public ResponseEntity<?> editPicture (@RequestParam Long id,@RequestParam("file")MultipartFile file){
		byte[] profileImage;
		System.out.println(id);
		try {
			profileImage = file.getBytes();
			String res = userService.updateProfileImage(profileImage, id);
			return ResponseEntity.ok(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body("update failed");
		}
		
	}
	
	@GetMapping("manage-user")
	public ResponseEntity<?> getAllUser (){
		List<User> allUser = userService.getAllUser();
	return ResponseEntity.ok(allUser);

	}
	@GetMapping("delete-user")
	public ResponseEntity<?> deleteUser (@RequestParam Long id){
		
		String res = userService.deleteById(id);
		if(res!=null) {
			return ResponseEntity.ok(res);			
		}
		return ResponseEntity.badRequest().body("Delete failed,Please try again!");
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		System.out.println(user.getUserName());
	    // Check if the username is already in use
	    if (userService.existsByUsername(user.getUserName())) {
	        return ResponseEntity.badRequest().body("Username already exists.");
	    }

	    // Save the new user
	    user.setProvider("LOCAL");
	    User newUser = userService.saveUser(user);

	    if (newUser != null) {
	        return ResponseEntity.ok("Registration successful.");
	    } else {
	        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Failed to register user.");
	    }
	}

}
