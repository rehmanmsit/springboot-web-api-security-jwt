package com.rehmanmsit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rehmanmsit.repository.UserRepository;
import com.rehmanmsit.security.filter.JWTAuthenticationFilter;
import com.rehmanmsit.security.filter.JWTAuthorizationFilter;
import com.rehmanmsit.util.JWTUtil;

/**
*
* @author Rehman
*
*/

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
	        // remove csrf and state in session because in jwt we do not need them
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        // add jwt filters (1. authentication, 2. authorization)
	        .addFilter(new JWTAuthenticationFilter(authenticationManager(), this.jwtUtil))
	        .addFilter(new JWTAuthorizationFilter(authenticationManager(),  this.userRepository, this.jwtUtil))
	        .authorizeRequests()
	        // configure access rules
	        .antMatchers(HttpMethod.POST, "/login").permitAll()
	        .antMatchers("/user/*").hasAuthority("USER")
	        .antMatchers("/admin/*").hasAuthority("ADMIN")
	        .anyRequest().authenticated();

	}

}
