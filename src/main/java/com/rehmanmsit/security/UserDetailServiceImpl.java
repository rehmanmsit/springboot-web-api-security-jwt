package com.rehmanmsit.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rehmanmsit.repository.UserRepository;
import com.rehmanmsit.repository.entity.User;

/**
*
* @author Rehman
*
*/

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User>  currentUser = userRepo.findByEmail(username);
		
		return currentUser.map(user -> {
			Map<String, Object> addlFields = new HashMap<>();
			addlFields.put("designation", user.getDesignation());
			addlFields.put("name", user.getName());
			return new AppUser(username, user.getPassword(),
					true, true, true, true, user.getRoles(), addlFields);
		}).orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
