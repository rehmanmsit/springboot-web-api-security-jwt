package com.rehmanmsit.security.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.rehmanmsit.repository.UserRepository;
import com.rehmanmsit.repository.entity.User;
import com.rehmanmsit.util.JWTUtil;


/**
*
* @author Rehman
*
*/

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header to fetch JWT token
        String header = request.getHeader("Authorization");

        // Check for BEARER prefix
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, proceed for authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization")
                				 .replace("Bearer ","");

        if (jwtToken != null) {
            // fetch UserName from JWT
            String userName = jwtUtil.getUsernameFromToken(jwtToken);

            // check for userName in DB. If present, proceed for creating Authentication object
            if (userName != null) {
                Optional<User> userObj = userRepository.findByEmail(userName);
                
                return userObj.map(user -> {
        			return new UsernamePasswordAuthenticationToken(userName, null,
        					AuthorityUtils.createAuthorityList(user.getRoles().stream().map(role -> role.name()).toArray(String[]::new)));
        		}).orElseThrow(() -> new UsernameNotFoundException(userName));
                
            }
            return null;
        }
        return null;
    }

}
