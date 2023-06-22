package com.kyn.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableSpringConfigured
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userService;

	@Autowired
	private JwtUtil jWTTokenHelper;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .cors().and().csrf().disable()
	        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
	        .and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeRequests()
	            .antMatchers("/auth/**", "/api/register").permitAll()
	            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            .antMatchers("/api/manage-user","/api/delete-user").hasAnyRole("ADMIN")
	            .anyRequest().authenticated()
	        .and()
	        .addFilterBefore(new JWTAuthenticationFilter(userService, jWTTokenHelper), UsernamePasswordAuthenticationFilter.class);

	    http.headers().frameOptions().disable();
	}
}
