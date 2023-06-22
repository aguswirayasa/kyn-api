package com.kyn.auth;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.kyn.entity.Role;
import com.kyn.entity.User;
import com.kyn.request.FacebookRequest;
import com.kyn.response.AuthResponse;
import com.kyn.security.config.JwtUtil;
import com.kyn.service.UserService;

@Service
public class FacebookAuthService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	public AuthResponse authenticateFacebookLogin(FacebookRequest request) {
		// if user exist in database
		User user = userService.getUserByProviderId(request.getId());
		if(user==null) {
			//create newuser and save them to database
			User newUser = new User();
			String username = request.getUsername();
	        String[] nameParts = username.split(" ");
	        String firstName = nameParts[0];
	        String lastName = nameParts.length > 1 ? nameParts[1] : "";
        	newUser.setFirstName(firstName);
        	newUser.setLastName(lastName);
        	newUser.setProvider("FACEBOOK");
        	newUser.setProviderId(request.getId());
        	newUser.setUserName(username.toLowerCase().replaceAll("\\s+", "")); 
        	newUser.setPassword(request.getId());
        	newUser.setPictureUrl(request.getPicture());
        	newUser.setEmail(request.getEmail());
        	user = userService.saveUser(newUser);
		}
		// generate jwt token
		 String jwtToken = jwtUtil.generateToken(user.getUserName());
		 // prepare the response
		 AuthResponse res = new AuthResponse();
         res.setToken(jwtToken);
         res.setUsername(user.getUserName());
         res.setPicture(user.getPictureUrl());
         Role roles = user.getRole();         
	     res.setRole(roles);
         return res;
	}
}
