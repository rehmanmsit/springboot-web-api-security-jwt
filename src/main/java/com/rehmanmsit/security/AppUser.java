package com.rehmanmsit.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.rehmanmsit.repository.entity.Role;

import lombok.Getter;
import lombok.Setter;

/**
*
* @author Rehman
*
*/

@Getter
@Setter
public class AppUser extends User {
	
	private static final long serialVersionUID = 1L;
	private final List<Role> roles;
	private final Map<String, Object> fields;

	public AppUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			List<Role> roles, Map<String, Object> fields) {
		super(username, password, enabled, accountNonExpired, 
			  credentialsNonExpired, accountNonLocked, 
			  AuthorityUtils.createAuthorityList(roles.stream().map(role -> role.name()).toArray(String[]::new)));
		this.roles = roles;
		this.fields = fields;
	}

}
