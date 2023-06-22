package com.kyn.response;

import java.util.Set;

import com.kyn.entity.Role;

public class AuthResponse {
	String token;
	String username;
	String picture;
	Set<String> roles;
	Role role;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Set<String> roles) {
		this.roles = roles;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
