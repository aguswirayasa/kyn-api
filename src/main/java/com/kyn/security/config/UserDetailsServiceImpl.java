package com.kyn.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kyn.entity.Role;
import com.kyn.entity.User;
import com.kyn.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByuserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        Role role = user.getRole();
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(role.getName()) // Set appropriate roles for your application
                .build();
    }
}
