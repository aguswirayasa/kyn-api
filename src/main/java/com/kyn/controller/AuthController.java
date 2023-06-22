package com.kyn.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kyn.auth.FacebookAuthService;
import com.kyn.auth.GoogleAuthService;
import com.kyn.request.FacebookRequest;
import com.kyn.request.GoogleRequest;
import com.kyn.request.LoginRequest;
import com.kyn.response.AuthResponse;
import com.kyn.security.config.JwtUtil;

@RestController
@RequestMapping("/auth/")
public class AuthController {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	GoogleAuthService googleService;
	
	@Autowired
	FacebookAuthService facebookService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${facebook.redirect-uri}")
	 private String redirectUri;
	    
	@Value("${facebook.client-id}")
	 private String clientId;
	    
	@Value("${facebook.client-secret}")
	 private String clientSecret;
	
	@PostMapping("/google-login-callback")
	public ResponseEntity<?> handleGoogleLoginCallback(@RequestBody GoogleRequest request) {
	    System.out.println("in controller: " + request.getCredential());
	    if (request.getCredential() != null) {
	        AuthResponse res = googleService.authenticateGoogleLogin(request.getCredential());
	        return ResponseEntity.ok(res);
	    } 
	    System.out.println("Token is null.");
	    return ResponseEntity.badRequest().body("Login failed. Token is null.");
	        
	    
	}
	
	@PostMapping("/facebook-login-callback")
	public ResponseEntity<?> handleFacebookLoginCallback(@RequestBody FacebookRequest request) {
	    System.out.println("in controller: " + request.getEmail());
	 
	        AuthResponse res = facebookService.authenticateFacebookLogin(request);
	        if (res != null) {
	        return ResponseEntity.ok(res);
	        }
	    
	    return ResponseEntity.badRequest().body("Login failed. Cannot get responne.");
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> handleLogin(@RequestBody LoginRequest request) {
	    System.out.println("in controller: " + request.getUsername());

	    try {
	        final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails user = (UserDetails) authentication.getPrincipal();
	        String jwtToken = jwtUtil.generateToken(user.getUsername());
	        AuthResponse res = new AuthResponse();
	        res.setUsername(user.getUsername());
	        res.setToken(jwtToken);
	        Set<String> roles = user.getAuthorities().stream()
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.toSet());
	        res.setRole(roles);

	        return ResponseEntity.ok(res);
	    } catch (AuthenticationException e) {
	        // Authentication failed
	        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED)
	                .body("Authentication failed. Invalid username or password.");
	    }
	}

	


	    
	   
}
