package com.rehmanmsit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rehmanmsit.repository.UserRepository;
import com.rehmanmsit.repository.entity.Role;
import com.rehmanmsit.repository.entity.User;

/**
*
* @author Rehman
*
*/

@SpringBootApplication
public class SpringbootWebApiSecurityJwtApplication {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApiSecurityJwtApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	CommandLineRunner runner() {
		return args -> {
			
			User user1 = new User();
			user1.setId("0");
			user1.setName("Test1");
			user1.setEmail("test1@test.com");
			user1.setDesignation("Software Engineer1");
			user1.setPassword(bCryptPasswordEncoder.encode("Test1@12345"));
			user1.setRoles(List.of(Role.ADMIN));
			
			User user2 = new User();
			user2.setId("1");
			user2.setName("Test2");
			user2.setEmail("test2@test.com");
			user2.setDesignation("Software Engineer2");
			user2.setPassword(bCryptPasswordEncoder.encode("Test2@12345"));
			user2.setRoles(List.of(Role.USER));

			var users = List.of(user1, user2);
			
			userRepo.saveAll(users);
		};
	};

}
