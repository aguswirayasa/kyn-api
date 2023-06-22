package com.kyn.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kyn.entity.Role;
import com.kyn.entity.User;
import com.kyn.response.AuthResponse;
import com.kyn.security.config.JwtUtil;
import com.kyn.service.UserService;


@Service
public class GoogleAuthService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
    final String CLIENT_ID = "817670283582-f05p3jir6vgrv28ap8gnk62schlb05m3.apps.googleusercontent.com";
    
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    public AuthResponse authenticateGoogleLogin(String idTokenString) {
        HttpTransport transport = new NetHttpTransport();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken;
        System.out.println(idTokenString);
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException | IllegalArgumentException e) {
        	e.printStackTrace();
            System.out.println("Failed to verify ID token: " + e.getMessage());
            return null;
        }

        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Get profile information from payload
            String userId = payload.getSubject();
          
            // Check if user already exist in database
            User user = userService.getUserByProviderId(userId);
            // Register user if not exist yet
            if(user==null) {
            	String email = payload.getEmail(); 
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
            	User newUser = new User();
            	newUser.setFirstName(familyName);
            	newUser.setLastName(givenName);
            	newUser.setProvider("GOOGLE");
            	newUser.setProviderId(userId);
            	newUser.setUserName(name.toLowerCase().replaceAll("\\s+", "")); 
            	newUser.setPassword(userId);
            	newUser.setPictureUrl(pictureUrl);
            	newUser.setEmail(email);
            	user = userService.saveUser(newUser);
            }
            String jwtToken = jwtUtil.generateToken(user.getUserName());
            AuthResponse res = new AuthResponse();
            res.setToken(jwtToken);
            res.setUsername(user.getUserName());
            res.setPicture(user.getPictureUrl());
            Role roles = user.getRole();
            
	        res.setRole(roles);
            return res;
        } else {
            System.out.println("Invalid ID token.");
        }
		return null;
    }
}
