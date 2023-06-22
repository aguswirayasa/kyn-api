package com.kyn.security.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsServiceImpl userDetailsService;
    private JwtUtil jwtTokenHelper;

    public JWTAuthenticationFilter(UserDetailsServiceImpl userDetailsService, JwtUtil jwtTokenHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authToken = getTokenFromCookie(request);

        if (null != authToken) {
            String userName = jwtTokenHelper.extractUsername(authToken);

            if (null != userName) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("_auth")) { // Replace "your_cookie_name" with the actual cookie name
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
