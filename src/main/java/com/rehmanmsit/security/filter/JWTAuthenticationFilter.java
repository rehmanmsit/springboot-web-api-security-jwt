package com.rehmanmsit.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rehmanmsit.controller.vo.UsrPwdVO;
import com.rehmanmsit.security.AppUser;
import com.rehmanmsit.util.JWTUtil;

/**
*
* @author Rehman
*
*/

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /* 
     * Triggers on POST request to /login
     * Need to pass in {"username":"XXXXXXX", "password":"XXXXXXX"} in the request body
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    	UsrPwdVO credentials = null;
    	
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), UsrPwdVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Grab principal
    	AppUser user = (AppUser) authResult.getPrincipal();
    	
    	String jwtToken = jwtUtil.generateToken(user);

        // Add token in response
        response.addHeader("Authorization", "Bearer " +  jwtToken);
    }
	
	

}
